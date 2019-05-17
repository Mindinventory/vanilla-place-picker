package com.vanillaplacepicker.data

import com.google.gson.annotations.SerializedName

class PlaceDetailsResponse {

    @SerializedName("result")
    var result: ResultBean? = null
    @SerializedName("status")
    var status: String? = null
    @SerializedName("html_attributions")
    var html_attributions: List<*>? = null
    @SerializedName("error_message")
    var errorMessage: String? = null

    class ResultBean {
        @SerializedName("adr_address")
        var adrAddress: String? = null
        @SerializedName("formatted_address")
        var formattedAddress: String? = null
        @SerializedName("geometry")
        var geometry: GeometryBean? = null
        @SerializedName("icon")
        var icon: String? = null
        @SerializedName("id")
        var id: String? = null
        @SerializedName("name")
        var name: String? = null
        @SerializedName("place_id")
        var placeId: String? = null
        @SerializedName("plus_code")
        var plusCode: PlusCodeBean? = null
        @SerializedName("scope")
        var scope: String? = null
        @SerializedName("url")
        var url: String? = null
        @SerializedName("utc_offset")
        var utcOffset: Int = 0
        @SerializedName("vicinity")
        var vicinity: String? = null
        @SerializedName("address_components")
        var addressComponents: List<AddressComponentsBean>? = null
        @SerializedName("photos")
        var photos: List<PhotosBean>? = null
        @SerializedName("types")
        var types: List<String>? = null

        class GeometryBean {
            @SerializedName("location")
            var location: LocationBean? = null
            @SerializedName("viewport")
            var viewport: ViewportBean? = null

            class LocationBean {
                @SerializedName("lat")
                var lat: Double = 0.toDouble()
                @SerializedName("lng")
                var lng: Double = 0.toDouble()
            }

            class ViewportBean {
                @SerializedName("northeast")
                var northeast: NortheastBean? = null
                @SerializedName("southwest")
                var southwest: SouthwestBean? = null

                class NortheastBean {
                    @SerializedName("lat")
                    var lat: Double = 0.toDouble()
                    @SerializedName("lng")
                    var lng: Double = 0.toDouble()
                }

                class SouthwestBean {
                    @SerializedName("lat")
                    var lat: Double = 0.toDouble()
                    @SerializedName("lng")
                    var lng: Double = 0.toDouble()
                }
            }
        }

        class PlusCodeBean {
            @SerializedName("compound_code")
            var compoundCode: String? = null
            @SerializedName("global_code")
            var globalCode: String? = null
        }

        class AddressComponentsBean {
            @SerializedName("long_name")
            var longName: String? = null
            @SerializedName("short_name")
            var shortName: String? = null
            @SerializedName("types")
            var types: List<String>? = null
        }

        class PhotosBean {
            @SerializedName("height")
            var height: Int = 0
            @SerializedName("photo_reference")
            var photoReference: String? = null
            @SerializedName("width")
            var width: Int = 0
            @SerializedName("html_attributions")
            var htmlAttributions: List<String>? = null
        }
    }
}
