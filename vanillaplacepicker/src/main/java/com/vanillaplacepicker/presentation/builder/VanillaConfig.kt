package com.vanillaplacepicker.presentation.builder

import android.os.Parcelable
import com.vanillaplacepicker.utils.KeyUtils
import com.vanillaplacepicker.utils.VanillaMapType
import com.vanillaplacepicker.utils.VanillaPickerType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VanillaConfig(
    var apiKey: String = "",
    var vanillaPickerType: VanillaPickerType = VanillaPickerType.MAP_WITH_AUTO_COMPLETE,
    var region: String? = null,
    var latitude: Double = KeyUtils.DEFAULT_LOCATION,
    var longitude: Double = KeyUtils.DEFAULT_LOCATION,
    var radius: Int? = null,
    var language: String? = null,
    var minPrice: Int? = null,
    var maxPrice: Int? = null,
    var isOpenNow: Boolean? = null,
    var pageToken: String? = null,
    var types: String? = null,
    var tintColor: Int? = null,
    var minCharLimit: Int = KeyUtils.DEFAULT_MIN_CHAR,
    var zoneLocale: String? = null,
    var zoneRect: SearchZoneRect? = null,
    var zoneDefaultLocale: Boolean = false,
    var enableSatelliteView: Boolean = false,
    var googleTimeZoneEnabled: Boolean = false,
    var mapStyleJSONResId: Int = KeyUtils.DEFAULT_STYLE_JSON_RESID,
    var mapType: VanillaMapType = VanillaMapType.MAP_TYPE_NORMAL,
    var mapPinDrawable: Int? = null
) : Parcelable