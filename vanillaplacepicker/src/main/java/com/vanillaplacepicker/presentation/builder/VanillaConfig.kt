package com.vanillaplacepicker.presentation.builder

import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.vanillaplacepicker.utils.KeyUtils
import com.vanillaplacepicker.utils.MapType
import com.vanillaplacepicker.utils.PickerType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VanillaConfig(
    var apiKey: String = "",
    var pickerType: PickerType = PickerType.MAP_WITH_AUTO_COMPLETE,
    var country: String? = null,
    var latitude: Double = KeyUtils.DEFAULT_LOCATION,
    var longitude: Double = KeyUtils.DEFAULT_LOCATION,
    var radius: Int? = null,
    var language: String? = null,
    var types: String? = null,
    var zoneRect: SearchZoneRect? = null,
    var enableSatelliteView: Boolean = false,
    var mapStyleJSONResId: Int = KeyUtils.DEFAULT_STYLE_JSON_RESID,
    var mapType: MapType = MapType.NORMAL,
    @DrawableRes var mapPinDrawable: Int? = null
) : Parcelable