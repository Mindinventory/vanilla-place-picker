package com.vanillaplacepicker.data.common

import com.google.android.libraries.places.api.model.Place
import com.vanillaplacepicker.data.VanillaAddress

object AutoCompleteAddressDetailsMapper : BaseMapper<Place, VanillaAddress>() {

    override fun map(oldItem: Place): VanillaAddress {
        return VanillaAddress().apply {
            this.formattedAddress = oldItem.address
            this.name = oldItem.name
            this.latitude = oldItem.latLng?.latitude
            this.longitude = oldItem.latLng?.longitude
            oldItem.addressComponents?.asList()?.forEach {
                    when {
                        it.types.contains("locality") -> this.locality = it.name
                        it.types.contains("sublocality_level_1") -> this.subLocality = it.name
                        it.types.contains("postal_code") -> this.postalCode = it.name
                        it.types.contains("country") -> {
                            this.countryCode = it.shortName
                            this.countryName = it.name
                    }
                }
            }
        }
    }
}