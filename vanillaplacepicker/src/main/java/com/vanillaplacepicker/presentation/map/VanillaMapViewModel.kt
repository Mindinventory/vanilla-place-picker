package com.vanillaplacepicker.presentation.map

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.vanillaplacepicker.presentation.common.VanillaBaseViewModel
import com.vanillaplacepicker.utils.SharedPrefs

class VanillaMapViewModel(private val sharedPrefs: SharedPrefs) : VanillaBaseViewModel() {

    var latLngLiveData = MutableLiveData<LatLng>()

    fun fetchSavedLocation() {
        val latitude = sharedPrefs.deviceLatitude.toDouble()
        val longitude = sharedPrefs.deviceLongitude.toDouble()
        if (latitude != 0.0 || longitude != 0.0) {
            latLngLiveData.value = LatLng(latitude, longitude)
        }
    }

    fun saveLatLngToSharedPref(latitude: Double, longitude: Double) {
        sharedPrefs.deviceLatitude = latitude.toFloat()
        sharedPrefs.deviceLongitude = longitude.toFloat()
    }
}