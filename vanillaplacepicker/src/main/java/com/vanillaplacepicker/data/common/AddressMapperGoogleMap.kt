package com.vanillaplacepicker.data.common

import com.vanillaplacepicker.data.GeoCoderAddressResponse
import com.vanillaplacepicker.data.VanillaAddress

object AddressMapperGoogleMap : BaseMapper<GeoCoderAddressResponse, VanillaAddress>() {

    override fun map(oldItem: GeoCoderAddressResponse): VanillaAddress {
        return VanillaAddress().apply {
            this.formattedAddress = oldItem.addressLine
            this.name = oldItem.featureName
            this.locality = oldItem.locality
            this.subLocality = oldItem.subAdminArea
            this.latitude = oldItem.latitude
            this.longitude = oldItem.longitude
            this.postalCode = oldItem.postalCode
            this.countryCode = oldItem.countryCode
            this.countryName = oldItem.countryName
        }
    }
}