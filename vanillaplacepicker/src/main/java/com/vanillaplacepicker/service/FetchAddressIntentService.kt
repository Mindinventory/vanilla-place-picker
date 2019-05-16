package com.vanillaplacepicker.service

import android.app.IntentService
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.os.ResultReceiver
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.vanillaplacepicker.R
import com.vanillaplacepicker.data.GeoCoderAddressResponse
import com.vanillaplacepicker.utils.KeyUtils
import com.vanillaplacepicker.utils.Logger
import java.io.IOException
import java.util.*

class FetchAddressIntentService : IntentService("FetchAddressIntentService") {
    private val TAG = FetchAddressIntentService::class.java.simpleName
    /**
     * The receiver where results are forwarded from this service.
     */
    private var receiver: ResultReceiver? = null

    /**
     * Tries to get the location address using a Geocoder. If successful, sends an address to a
     * result receiver. If unsuccessful, sends an error message instead.
     * Note: We define a [android.os.ResultReceiver] in * VanillaMapActivity to process content
     * sent from this service.
     *
     * This service calls this method from the default worker thread with the intent that started
     * the service. When this method returns, the service automatically stops.
     */
    override fun onHandleIntent(intent: Intent?) {
        var errorMessage = ""

        receiver = intent?.getParcelableExtra(KeyUtils.RECEIVER)

        // Check if receiver was properly registered.
        if (intent == null || receiver == null) {
            Log.wtf(TAG, "No receiver received. There is nowhere to send the results.")
            return
        }

        // Get the location passed to this service through an extra.
        val latlng = intent.getParcelableExtra<LatLng>(KeyUtils.LOCATION_DATA_EXTRA)

        // Make sure that the location data was really sent over through an extra. If it wasn't,
        // send an error error message and return.
        if (latlng == null) {
            errorMessage = getString(R.string.no_location_data_provided)
            Log.wtf(TAG, errorMessage)
            deliverResultToReceiver(KeyUtils.FAILURE_RESULT, errorMessage, null)
            return
        }

        // Errors could still arise from using the Geocoder (for example, if there is no
        // connectivity, or if the Geocoder is given illegal location data). Or, the Geocoder may
        // simply not have an address for a location. In all these cases, we communicate with the
        // receiver using a resultCode indicating failure. If an address is found, we use a
        // resultCode indicating success.

        // The Geocoder used in this sample. The Geocoder's responses are localized for the given
        // Locale, which represents a specific geographical or linguistic region. Locales are used
        // to alter the presentation of information such as numbers or dates to suit the conventions
        // in the region they describe.
        val geocoder = Geocoder(this, Locale.getDefault())

        // Address found using the Geocoder.
        var addresses: List<Address> = emptyList()

        try {
            // Using getFromLocation() returns an array of Addresses for the area immediately
            // surrounding the given latitude and longitude. The results are a best guess and are
            // not guaranteed to be accurate.
            addresses = geocoder.getFromLocation(
                    latlng.latitude,
                    latlng.longitude,
                    // In this sample, we get just a single address.
                    1
            )
        } catch (ioException: IOException) {
            // Catch network or other I/O problems.
            Logger.e(TAG, ioException.toString())
            deliverResultToReceiver(KeyUtils.FAILURE_RESULT, getString(R.string.service_not_available), null)
        } catch (illegalArgumentException: IllegalArgumentException) {
            // Catch invalid latitude or longitude values.
            Logger.e(
                    TAG, "Latitude = $latlng.latitude , " +
                    "Longitude = $latlng.longitude $illegalArgumentException"
            )
            deliverResultToReceiver(KeyUtils.FAILURE_RESULT, getString(R.string.invalid_lat_long_used), null)
        }

        // Handle case where no address was found.
        if (addresses.isEmpty()) {
            if (errorMessage.isEmpty()) {
                errorMessage = getString(R.string.no_address_found)
                Logger.e(TAG, errorMessage)
            }
            deliverResultToReceiver(KeyUtils.FAILURE_RESULT, errorMessage, null)
        } else {
            Logger.d(TAG, "Addresses >> ${Gson().toJson(addresses)}")
            val address = addresses[0]
            // Fetch the address lines using {@code getAddressLine},
            // join them, and send them to the thread. The {@link android.location.address}
            // class provides other options for fetching address details that you may prefer
            // to use. Here are some examples:
            // getLocality() ("Mountain View", for example)
            // getAdminArea() ("CA", for example)
            // getPostalCode() ("94043", for example)
            // getCountryCode() ("US", for example)
            // getCountryName() ("United States", for example)
            val addressFragments = with(address) {
                (0..maxAddressLineIndex).map { getAddressLine(it) }
            }

            val selectedPlace = GeoCoderAddressResponse(
                    addressFragments.joinToString(separator = "\n"),
                    address.featureName,
                    address.adminArea,
                    address.subAdminArea,
                    address.locality,
                    address.subThoroughfare,
                    address.postalCode,
                    address.countryCode,
                    address.countryName,
                    address.hasLatitude(),
                    address.latitude,
                    address.hasLongitude(),
                    address.longitude,
                    address.phone,
                    address.url,
                    address.extras
            )

            Logger.i(TAG, getString(R.string.address_found))
            deliverResultToReceiver(
                    KeyUtils.SUCCESS_RESULT,
                    getString(R.string.address_found),
                    selectedPlace
            )
        }
    }

    /**
     * Sends a resultCode and message to the receiver.
     */
    private fun deliverResultToReceiver(resultCode: Int, message: String, selectedPlace: GeoCoderAddressResponse?) {
        val bundle = Bundle().apply {
            putString(KeyUtils.RESULT_MESSAGE_KEY, message)
            putSerializable(KeyUtils.RESULT_DATA_KEY, selectedPlace)
        }
        receiver?.send(resultCode, bundle)
    }
}