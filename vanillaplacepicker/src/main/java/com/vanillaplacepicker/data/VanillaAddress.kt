package com.vanillaplacepicker.data

import java.io.Serializable

data class VanillaAddress(
        var formattedAddress: String? = null,
        var name: String? = null,
        var placeId: String? = null,
        var latitude: Double? = null,
        var longitude: Double? = null,
        var locality: String? = null,
        var subLocality: String? = null,
        var postalCode: String? = null,
        var countryCode: String? = null,
        var countryName: String? = null
) : Serializable