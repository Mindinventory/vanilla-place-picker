package com.vanillaplacepicker.presentation.builder

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.widget.Autocomplete
import com.vanillaplacepicker.data.VanillaAddress
import com.vanillaplacepicker.data.common.AutoCompleteAddressDetailsMapper
import com.vanillaplacepicker.extenstion.isRequiredField
import com.vanillaplacepicker.presentation.map.VanillaMapActivity
import com.vanillaplacepicker.utils.*
import wrap


class VanillaPlacePicker {

    companion object {
        val TAG = VanillaPlacePicker::class.java.simpleName

        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): VanillaAddress? {
            if (resultCode == Activity.RESULT_OK && data != null) {
                when (requestCode) {
                    KeyUtils.REQUEST_PLACE_PICKER -> {
                        val selectedPlace = data.getSerializableExtra(KeyUtils.SELECTED_PLACE)
                        return if (selectedPlace is VanillaAddress) {
                            selectedPlace
                        } else {
                            val place = Autocomplete.getPlaceFromIntent(data)
                            AutoCompleteAddressDetailsMapper.apply(place)
                        }
                    }
                }
            }
            return null
        }
    }

    class Builder(private val context: Context) {
        private val vanillaConfig = VanillaConfig()

        /**
         * To enable map view with place picker
         */
        fun with(pickerType: PickerType): Builder {
            vanillaConfig.pickerType = pickerType
            return this
        }

        /**
         * Filter addresses by country name ex. "US"
         */
        fun setCountry(country: String): Builder {
            vanillaConfig.country = country
            return this
        }

        /**
         * Request with default latitude & longitude for near by places
         */
        fun withLocation(latitude: Double, longitude: Double): Builder {
            vanillaConfig.latitude = latitude
            vanillaConfig.longitude = longitude
            return this
        }

        /**
         * To restrict request zone by rect
         */
        fun setLocationRestriction(leftLatLng: LatLng, rightLatLng: LatLng): Builder {
            vanillaConfig.zoneRect = SearchZoneRect(leftLatLng, rightLatLng)
            return this
        }

        /**
         * Add a map with custom styling
         * With style options you can customize the presentation of the standard Google map styles,
         * changing the visual display of features like roads, parks, businesses, and other points of interest.
         * Add a resource containing a JSON style object (Use a raw resource)
         * */
        fun setMapStyle(jsonResourceIdMapStyle: Int): Builder {
            vanillaConfig.mapStyleJSONResId = jsonResourceIdMapStyle
            return this
        }

        /**
         * Add a map with custom styling
         * With style options you can customize the presentation of the standard Google map styles,
         * changing the visual display of features like roads, parks, businesses, and other points of interest.
         * Add a resource containing a JSON style object (Use a string resource)
         * */
        fun setMapType(mapType: MapType): Builder {
            vanillaConfig.mapType = mapType
            return this
        }

        /**
         * Set custom Map Pin image
         * */
        fun setMapPinDrawable(mapPinDrawableResId: Int): Builder {
            vanillaConfig.mapPinDrawable = mapPinDrawableResId
            return this
        }

        /**
         * Set picker language
         */
        fun setPickerLanguage(pickerLanguage: PickerLanguage): Builder {
            ContextWrapper(context).wrap(pickerLanguage.value)
            return this
        }

        /**
         * Get Google Places API key
         */
        private fun getApiKey(): String {
            val metadataBundle: Bundle? = BundleUtils.getMetaData(context)
            if (metadataBundle != null) {
                return if (metadataBundle.getString("com.google.android.geo.API_KEY").isRequiredField())
                    metadataBundle.getString("com.google.android.geo.API_KEY")!!
                else {
                    Log.e(
                        TAG,
                        "Couldn't get Google API key from application meta data. Was it set in your AndroidManifest.xml?"
                    )
                    ""
                }
            } else {
                Log.e(
                    TAG,
                    "Couldn't get Google API key from application meta data. Was it set in your AndroidManifest.xml?"
                )
                return ""
            }
        }

        fun build(): Intent {
            vanillaConfig.apiKey = getApiKey()
            val intent = if (vanillaConfig.pickerType == PickerType.AUTO_COMPLETE) {
                AutoCompleteUtils.getAutoCompleteIntent(context, vanillaConfig)
            } else {
                Intent(context, VanillaMapActivity::class.java)
            }
            intent.putExtra(KeyUtils.EXTRA_CONFIG, vanillaConfig)
            return intent
        }
    }
}