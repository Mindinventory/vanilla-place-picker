package com.vanillaplacepicker.presentation.map

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.*
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.vanillaplacepicker.R
import com.vanillaplacepicker.data.GeoCoderAddressResponse
import com.vanillaplacepicker.data.common.AddressMapperGoogleMap
import com.vanillaplacepicker.domain.common.SafeObserver
import com.vanillaplacepicker.extenstion.*
import com.vanillaplacepicker.presentation.builder.VanillaConfig
import com.vanillaplacepicker.presentation.common.VanillaBaseViewModelActivity
import com.vanillaplacepicker.service.FetchAddressIntentService
import com.vanillaplacepicker.utils.*
import kotlinx.android.synthetic.main.activity_vanilla_map.*
import kotlinx.android.synthetic.main.toolbar.*

class VanillaMapActivity : VanillaBaseViewModelActivity<VanillaMapViewModel>(), OnMapReadyCallback,
    View.OnClickListener {
    private val TAG = VanillaMapActivity::class.java.simpleName
    private var mapFragment: SupportMapFragment? = null
    private var googleMap: GoogleMap? = null
    private lateinit var resultReceiver: AddressResultReceiver
    private var selectedPlace: GeoCoderAddressResponse? = null
    private var midLatLng: LatLng? = null
    private var defaultZoomLoaded = false
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private val startLocationHandler = Handler()

    private lateinit var vanillaConfig: VanillaConfig
    // Belows are used in PlacePickerActivity
    private var isRequestedWithLocation = false
    private val sharedPrefs by lazy { SharedPrefs(this) }
    private var fetchLocationForFirstTime = false

    override fun buildViewModel(): VanillaMapViewModel {
        return ViewModelProviders.of(
            this,
            VanillaMapViewModelFactory(sharedPrefs)
        )[VanillaMapViewModel::class.java]
    }

    override fun getContentResource() = R.layout.activity_vanilla_map

    override fun initViews() {
        super.initViews()
        // HIDE ActionBar(if exist in style) of root project module
        supportActionBar?.hide()
        setMapPinDrawable()
        tvAddress.isSelected = true
        ivBack.setOnClickListener(this)
        ivDone.setOnClickListener(this)
        if (vanillaConfig.pickerType == PickerType.MAP_WITH_AUTO_COMPLETE) {
            tvAddress.setOnClickListener(this)
        }
        fabLocation.setOnClickListener(this)
        if (!isRequestedWithLocation) {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
            findLocation()
        }
        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        resultReceiver = AddressResultReceiver(Handler())
    }

    override fun getBundle() {
        if (hasExtra(KeyUtils.EXTRA_CONFIG)) {
            vanillaConfig = intent.getParcelableExtra(KeyUtils.EXTRA_CONFIG)
        }
        if (vanillaConfig.latitude != KeyUtils.DEFAULT_LOCATION && vanillaConfig.longitude != KeyUtils.DEFAULT_LOCATION) {
            isRequestedWithLocation = true
        }
    }

    // Set custom image drawable to Map Pin
    private fun setMapPinDrawable() {
        try {
            vanillaConfig.mapPinDrawable?.let { pinDrawableResId ->
                ivMarker.setImageDrawable(ContextCompat.getDrawable(this, pinDrawableResId))
            }
        } catch (e: Exception) {
            Logger.e(TAG, "Invalid drawable resource ID. Error: $e")
        }
    }

    /**
     * this runnable will help to provide adequate delay before fetching device location.
     */
    private val startLocationRunnable = Runnable {
        getLocationFromFusedLocation()
    }

    private fun removeCallbacks() {
        startLocationHandler.removeCallbacks(startLocationRunnable)
    }

    private fun postDelayed() {
        startLocationHandler.postDelayed(startLocationRunnable, KeyUtils.LOCATION_UPDATE_INTERVAL)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivBack -> onBackPressed()
            R.id.ivDone -> {
                selectedPlace ?: return
                setResult(Activity.RESULT_OK, Intent().apply {
                    putExtra(
                        KeyUtils.SELECTED_PLACE,
                        selectedPlace?.let { AddressMapperGoogleMap.apply(it) })
                })
                finish()
            }
            R.id.tvAddress -> startActivityForResult(
                AutoCompleteUtils.getAutoCompleteIntent(this, vanillaConfig),
                KeyUtils.REQUEST_PLACE_PICKER
            )
            R.id.fabLocation -> isGpsEnabled()
        }
    }

    /**
     * Receiver for data sent from FetchAddressIntentService.
     */
    private inner class AddressResultReceiver internal constructor(
        handler: Handler
    ) : ResultReceiver(handler) {
        /**
         * Receives data sent from FetchAddressIntentService and updates the UI.
         */
        override fun onReceiveResult(resultCode: Int, resultData: Bundle) {
            if (!resultData.containsKey(KeyUtils.RESULT_DATA_KEY)) {
                return
            }
            when (resultCode) {
                KeyUtils.SUCCESS_RESULT -> {
                    selectedPlace =
                        resultData.getSerializable(KeyUtils.RESULT_DATA_KEY) as GeoCoderAddressResponse
                    if (selectedPlace?.addressLine.isRequiredField()) {
                        Logger.d(
                            TAG,
                            "AddressResultReceiver.onReceiveResult: address: ${selectedPlace?.addressLine}"
                        )
                        ivDone.showView()
                        tvAddress.text = selectedPlace?.addressLine
                    } else {
                        ivDone.hideView()
                    }
                }
                KeyUtils.FAILURE_RESULT -> {
                    val errorMessage = resultData.getString(KeyUtils.RESULT_MESSAGE_KEY)
                    ToastUtils.showToast(this@VanillaMapActivity, errorMessage)
                }
                else -> {
                    // make address empty
                }
            }
        }
    }

    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready for use.
     */
    override fun onMapReady(googleMap: GoogleMap?) {
        this.googleMap = googleMap
        this.googleMap?.clear()
        if (vanillaConfig.enableSatelliteView)
            this.googleMap?.mapType = GoogleMap.MAP_TYPE_SATELLITE

        // Customise the styling of the base map using a JSON object defined...
        try {
            // ...in a raw resource file.
            if (vanillaConfig.mapStyleJSONResId != KeyUtils.DEFAULT_STYLE_JSON_RESID) {
                this.googleMap?.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                        this@VanillaMapActivity,
                        vanillaConfig.mapStyleJSONResId
                    )
                )
            }
            // ...in a string resource file.
            this.googleMap?.mapType = vanillaConfig.mapType.value
        } catch (e: Exception) {
            Logger.e(TAG, "Can't find map style or Style parsing failed. Error: $e")
        }
        val cameraUpdateDefaultLocation = CameraUpdateFactory.newLatLngZoom(
            LatLng(vanillaConfig.latitude, vanillaConfig.longitude),
            if (vanillaConfig.latitude == KeyUtils.DEFAULT_LOCATION) 0f else KeyUtils.DEFAULT_ZOOM_LEVEL
        )
        this.googleMap?.animateCamera(
            cameraUpdateDefaultLocation,
            KeyUtils.GOOGLE_MAP_CAMERA_ANIMATE_DURATION,
            null
        )
        /**
         * Set Padding: Top to show CompassButton at visible position on map
         * (Before allow Location runtime permission, After that changed position of CompassButton)
         * */
        this.googleMap?.setPadding(0, 256, 0, 0)
        vanillaConfig.zoneRect?.let {
            this.googleMap?.setLatLngBoundsForCameraTarget(
                LatLngBounds(
                    it.upperRight,
                    it.lowerLeft
                )
            )
        }

        this.googleMap?.setOnCameraMoveListener {
            tvAddress.text = getString(R.string.searching)
            ivDone.hideView()
        }
        this.googleMap?.setOnCameraIdleListener {
            val newLatLng = this@VanillaMapActivity.googleMap?.cameraPosition?.target
            newLatLng?.let {
                midLatLng?.let { midLatLng ->
                    if (it.latitude == midLatLng.latitude && it.longitude == midLatLng.longitude) {
                        return@setOnCameraIdleListener
                    }
                }
                midLatLng = this@VanillaMapActivity.googleMap?.cameraPosition?.target
                midLatLng?.let { centerPoint ->
                    startReverseGeoCodingService(centerPoint)
                }
            }
        }
    }

    override fun initLiveDataObservers() {
        super.initLiveDataObservers()
        viewModel.latLngLiveData.observe(this, SafeObserver(this::moveCameraToLocation))
    }

    private fun moveCameraToLocation(latLng: LatLng?) {
        latLng ?: return

        val zoomLevel = if (defaultZoomLoaded) {
            defaultZoomLoaded = false
            googleMap?.cameraPosition?.zoom ?: KeyUtils.DEFAULT_ZOOM_LEVEL
        } else {
            KeyUtils.DEFAULT_ZOOM_LEVEL
        }
        val location = CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel)
        googleMap?.animateCamera(location)

        changeLocationCompassButtonPosition()
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            this.googleMap?.isMyLocationEnabled = true
            this.googleMap?.uiSettings?.isMyLocationButtonEnabled = false
            changeMyLocationButtonPosition()
        }
    }

    /*
    * Change location compass button position
    * From: Default
    * To: Top - Right
    * */
    private fun changeLocationCompassButtonPosition() {
        try {
            val locationCompassButton =
                (mapFragment?.view?.findViewById<View>(Integer.parseInt("1"))?.parent as View)
                    .findViewById<View>(Integer.parseInt("5"))
            val rlp = locationCompassButton.layoutParams as RelativeLayout.LayoutParams
            rlp.addRule(RelativeLayout.ALIGN_PARENT_START, 0)
            rlp.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE)
            rlp.marginEnd = 16
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    /*
    * Change my location button position
    * From: Default
    * To: Bottom - Right
    * */
    private fun changeMyLocationButtonPosition() {
        val locationButton =
            (mapFragment?.view?.findViewById<View>(Integer.parseInt("1"))?.parent as View)
                .findViewById<View>(Integer.parseInt("2"))
        val rlp = locationButton.layoutParams as RelativeLayout.LayoutParams
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0)
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
        rlp.setMargins(0, 0, 0, 32)
    }

    /**
     * Creates an intent, adds location data to it as an extra, and starts the intent service for
     * fetching an address.
     */
    private fun startReverseGeoCodingService(latLng: LatLng) {
        val lat = latLng.latitude
        val lng = latLng.longitude

        if (lat != KeyUtils.DEFAULT_LOCATION && lng != KeyUtils.DEFAULT_LOCATION) {
            // Create an intent for passing to the intent service responsible for fetching the address.
            val intent = Intent(this, FetchAddressIntentService::class.java).apply {
                // Pass the result receiver as an extra to the service.
                putExtra(KeyUtils.RECEIVER, resultReceiver)
                // Pass the location data as an extra to the service.
                putExtra(KeyUtils.LOCATION_DATA_EXTRA, latLng)
            }
            // Start the service. If the service isn't already running, it is instantiated and started
            // (creating a process for it if needed); if it is running then it remains running. The
            // service kills itself automatically once all intents are processed.
            startService(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            KeyUtils.REQUEST_CHECK_SETTINGS -> when (resultCode) {
                Activity.RESULT_CANCELED -> viewModel.fetchSavedLocation()
                Activity.RESULT_OK -> postDelayed()
            }
            // Check for the integer request code originally supplied to PlacePickerActivityForResult()
            KeyUtils.REQUEST_PLACE_PICKER -> when (resultCode) {
                Activity.RESULT_OK -> {
                    // data contains Place object
                    setResult(Activity.RESULT_OK, data)
                    finish()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            KeyUtils.REQUEST_PERMISSIONS_REQUEST_CODE -> {
                when {
                    grantResults.isEmpty() -> {
                        // If user interaction was interrupted, the permission request is cancelled and you receive empty arrays.
                        Log.d(TAG, resources.getString(R.string.user_interaction_was_cancelled))
                    }
                    grantResults[0] == PackageManager.PERMISSION_GRANTED -> startLocationUpdates()
                    else -> showAlertDialog(
                        R.string.missing_permission_message,
                        R.string.missing_permission_title,
                        R.string.permission,
                        R.string.cancel, {
                            // this mean user has clicked on permission button to update run time permission.
                            openAppSetting()
                        }
                    )
                }
            }
        }
    }

    /**
     * this function will help to trigger location request
     * please do not make this function as private as we are using this one to trigger location related flow from client base.
     */
    private fun findLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // this mean device os is greater or equal to Marshmallow.
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // here we are going to request location run time permission.
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    KeyUtils.REQUEST_PERMISSIONS_REQUEST_CODE
                )
                return
            }
        }
        startLocationUpdates()
    }

    private val locationRequest = LocationRequest().apply {
        this.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        this.interval = KeyUtils.DEFAULT_FETCH_LOCATION_INTERVAL
    }

    private val locationSettingRequest = LocationSettingsRequest.Builder()
        .addLocationRequest(locationRequest)

    /**
     * this method will check required for location and according to result it will go ahead for fetching location.
     */
    private fun startLocationUpdates() {
        // Begin by checking if the device has the necessary location settings.
        LocationServices.getSettingsClient(this).checkLocationSettings(locationSettingRequest.build())!!
            .addOnSuccessListener(this) {
                getLocationFromFusedLocation()
            }.addOnFailureListener(this) { e ->
                when ((e as ApiException).statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        Logger.i(
                            TAG,
                            resources.getString(R.string.location_settings_are_not_satisfied)
                        )
                        try {
                            val rae = e as ResolvableApiException
                            rae.startResolutionForResult(this, KeyUtils.REQUEST_CHECK_SETTINGS)
                        } catch (sie: IntentSender.SendIntentException) {
                            Logger.i(
                                TAG,
                                getString(R.string.pendingintent_unable_to_execute_request)
                            )
                            viewModel.fetchSavedLocation()
                            sie.printStackTrace()
                        }
                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                        val errorMessage =
                            resources.getString(R.string.location_settings_are_inadequate_and_cannot_be_fixed_here)
                        Logger.e(TAG, errorMessage)
                        viewModel.fetchSavedLocation()
                    }
                }
            }
    }

    private fun getLocationFromFusedLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        fusedLocationProviderClient?.flushLocations()
        fusedLocationProviderClient?.requestLocationUpdates(
            locationRequest,
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    super.onLocationResult(locationResult)
                    val location = locationResult!!.lastLocation
                    if (location != null) {
                        viewModel.saveLatLngToSharedPref(location.latitude, location.longitude)
                    }
                    if (!fetchLocationForFirstTime) {
                        viewModel.fetchSavedLocation()
                        fetchLocationForFirstTime = true
                    }
                }

                override fun onLocationAvailability(locationAvailability: LocationAvailability?) {
                    super.onLocationAvailability(locationAvailability)
                    if (!locationAvailability!!.isLocationAvailable) {
                        viewModel.fetchSavedLocation()
                    }
                }
            },
            Looper.myLooper()
        )
    }


    private fun stopLocationUpdates() {
        // here, we are removing callback from runnable for handler so it will get called ahead.
        removeCallbacks()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopLocationUpdates()
    }

    private fun isGpsEnabled() {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var isGpeEnabled = false
        var isNetworkEnabled = false
        try {
            isGpeEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        } catch (e: Exception) {
            Logger.e(TAG, "isGpsEnabled >> $e")
        }

        try {
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        } catch (e: Exception) {
            Logger.e(TAG, "isNetworkEnabled >> $e")
        }

        if (!isGpeEnabled && !isNetworkEnabled) {
            //enable gps
            findLocation()
        } else {
            viewModel.fetchSavedLocation()
        }
    }
}
