package com.vanillaplacepicker.presentation.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.vanillaplacepicker.utils.SharedPrefs

class VanillaMapViewModel(private val sharedPrefs: SharedPrefs) : ViewModel() {

    var latLngLiveData = MutableLiveData<LatLng>()

    fun fetchSavedLocation() {
        val latitude = sharedPrefs.deviceLatitude.toDouble()
        val longitude = sharedPrefs.deviceLongitude.toDouble()
        latLngLiveData.postValue(LatLng(latitude, longitude))
    }

    fun saveLatLngToSharedPref(latitude: Double, longitude: Double) {
        sharedPrefs.deviceLatitude = latitude.toFloat()
        sharedPrefs.deviceLongitude = longitude.toFloat()
    }
}