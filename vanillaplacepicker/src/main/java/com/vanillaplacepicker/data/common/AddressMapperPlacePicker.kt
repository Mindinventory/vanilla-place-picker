package com.vanillaplacepicker.data.common

import com.vanillaplacepicker.data.SearchAddressResponse
import com.vanillaplacepicker.data.VanillaAddress


object AddressMapperPlacePicker : BaseMapper<SearchAddressResponse.Results, VanillaAddress>() {

    override fun map(oldItem: SearchAddressResponse.Results): VanillaAddress {
        return VanillaAddress().apply {
            this.formattedAddress = oldItem.formattedAddress
            this.name = oldItem.name
            this.placeId = oldItem.placeId
            this.latitude = oldItem.geometry?.location?.lat
            this.longitude = oldItem.geometry?.location?.lng
            val address: List<String>? = oldItem.formattedAddress?.trim()?.split(",")?.reversed()
            address?.let {
                // to seperate locality from formattedAddress
                this.locality = if (it.size > 2)
                    it[2].trim()
                else
                    null

                // to seperate postalCode from formattedAddress
                if (it.size > 1) {
                    val stateAndPostalCode: List<String>? = it[1].trim().split(" ")
                    stateAndPostalCode?.let {
                        this.postalCode = if (stateAndPostalCode.size > 1)
                            stateAndPostalCode[1].trim()
                        else
                            null
                    }
                }

                // to seperate countryName from formattedAddress
                this.countryName = if (it.isNotEmpty())
                    it[0].trim()
                else
                    null
            }
        }
    }
}