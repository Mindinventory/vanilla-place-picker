package com.vanillaplacepicker.data.common

import com.vanillaplacepicker.data.PlaceDetailsResponse
import com.vanillaplacepicker.data.VanillaAddress

object PlaceDetailsMapper : BaseMapper<PlaceDetailsResponse, VanillaAddress>() {

    override fun map(oldItem: PlaceDetailsResponse): VanillaAddress {
        return VanillaAddress().apply {
            this.formattedAddress = oldItem.result?.formattedAddress
            this.name = oldItem.result?.name
            this.latitude = oldItem.result?.geometry?.location?.lat
            this.longitude = oldItem.result?.geometry?.location?.lng
            oldItem.result?.addressComponents?.forEach {
                it.types?.let { types ->
                    when {
                        types.contains("locality") -> this.locality = it.longName
                        types.contains("sublocality_level_1") -> this.subLocality = it.longName
                        types.contains("postal_code") -> this.postalCode = it.longName
                        types.contains("country") -> {
                            this.countryCode = it.shortName
                            this.countryName = it.longName
                        }
                    }
                }
            }
        }
    }
}