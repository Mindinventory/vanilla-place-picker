package com.vanillaplacepicker.presentation.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vanillaplacepicker.utils.SharedPrefs

class VanillaMapViewModelFactory(private val sharedPrefs: SharedPrefs) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.cast(VanillaMapViewModel(sharedPrefs)) as T
    }
}