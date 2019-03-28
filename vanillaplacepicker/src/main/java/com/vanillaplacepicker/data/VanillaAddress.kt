package com.vanillaplacepicker.data

import java.io.Serializable

data class VanillaAddress(
    var formattedAddress: String?,
    var name: String?,
    var placeId: String?,
    var latitude: Double?,
    var longitude: Double?,
    var locality: String?,
    var postalCode: String?,
    var countryCode: String?,
    var countryName: String?
) : Serializable {
    constructor() : this(
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    )
}