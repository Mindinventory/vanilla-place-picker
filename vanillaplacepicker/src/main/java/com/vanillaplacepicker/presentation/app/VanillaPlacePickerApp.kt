package com.vanillaplacepicker.presentation.app

import android.app.Application
import com.vanillaplacepicker.utils.SharedPrefs

class VanillaPlacePickerApp: Application(){

    companion object {
        lateinit var sharedPrefs: SharedPrefs
    }

    override fun onCreate() {
        super.onCreate()
        sharedPrefs = SharedPrefs(this)
    }
}