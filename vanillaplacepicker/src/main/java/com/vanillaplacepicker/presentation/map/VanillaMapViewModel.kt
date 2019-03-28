package com.vanillaplacepicker.presentation.map

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.vanillaplacepicker.presentation.app.VanillaPlacePickerApp.Companion.sharedPrefs
import com.vanillaplacepicker.presentation.common.VanillaBaseViewModel

class VanillaMapViewModel : VanillaBaseViewModel() {

    var latLngLiveData = MutableLiveData<LatLng>()

    fun fetchSavedLocation() {
        latLngLiveData.value = (LatLng(
                sharedPrefs.deviceLatitude.toDouble(),
                sharedPrefs.deviceLongitude.toDouble()
        ))
    }

    fun saveLatLngToSharedPref(latitude: Double, longitude: Double) {
        sharedPrefs.deviceLatitude = latitude.toFloat()
        sharedPrefs.deviceLongitude = longitude.toFloat()
    }
}