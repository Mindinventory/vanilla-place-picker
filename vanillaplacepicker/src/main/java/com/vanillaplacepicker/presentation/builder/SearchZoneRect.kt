package com.vanillaplacepicker.presentation.builder

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchZoneRect(val lowerLeft: LatLng, val upperRight: LatLng) : Parcelable