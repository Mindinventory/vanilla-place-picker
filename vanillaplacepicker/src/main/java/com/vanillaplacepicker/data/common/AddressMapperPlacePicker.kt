package com.vanillaplacepicker.data.common

import com.vanillaplacepicker.data.VanillaAddress
import com.vanillaplacepicker.data.SearchAddressResponse

object AddressMapperPlacePicker : BaseMapper<SearchAddressResponse.Results, VanillaAddress>() {

    override fun map(oldItem: SearchAddressResponse.Results): VanillaAddress {
        return VanillaAddress().apply {
            this.formattedAddress = oldItem.formattedAddress
            this.name = oldItem.name
            this.placeId = oldItem.placeId
            this.latitude = oldItem.geometry?.location?.lat
            this.longitude = oldItem.geometry?.location?.lng
        }
    }
}