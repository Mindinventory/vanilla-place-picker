package com.vanillaplacepicker.utils

import com.google.android.gms.maps.GoogleMap

enum class MapType(val value: Int) {
    NORMAL(GoogleMap.MAP_TYPE_NORMAL),
    SATELLITE(GoogleMap.MAP_TYPE_SATELLITE),
    TERRAIN(GoogleMap.MAP_TYPE_TERRAIN),
    HYBRID(GoogleMap.MAP_TYPE_HYBRID),
    NONE(GoogleMap.MAP_TYPE_NONE)
}