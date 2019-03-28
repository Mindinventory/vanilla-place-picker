package com.vanillaplacepicker.data

import android.os.Bundle
import java.io.Serializable

data class GeoCoderAddressResponse(
    var addressLine: String?,
    var featureName: String?,
    var adminArea: String?,
    var subAdminArea: String?,
    var locality: String?,
    var thoroughfare: String?,
    var postalCode: String?,
    var countryCode: String?,
    var countryName: String?,
    var hasLatitude: Boolean?,
    var latitude: Double?,
    var hasLongitude: Boolean?,
    var longitude: Double?,
    var phone: String?,
    var url: String?,
    var extras: Bundle?
) : Serializable