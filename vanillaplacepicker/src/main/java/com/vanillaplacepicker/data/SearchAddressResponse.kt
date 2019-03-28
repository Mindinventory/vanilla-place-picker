package com.vanillaplacepicker.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class SearchAddressResponse : Serializable {
    @SerializedName("html_attributions")
    var htmlAttributions: Array<String>? = null

    @SerializedName("results")
    var results: ArrayList<Results> = ArrayList()

    @SerializedName("status")
    var status: String = ""

    @SerializedName("error_message")
    var errorMessage: String = ""

    inner class Results : Serializable {
        @SerializedName("formatted_address")
        var formattedAddress: String? = null

        @SerializedName("geometry")
        var geometry: Geometry? = null

        @SerializedName("icon")
        var icon: String? = null

        @SerializedName("id")
        var id: String? = null

        @SerializedName("name")
        var name: String? = null

        @SerializedName("opening_hours")
        var openingHours: OpeningHours? = null

        @SerializedName("photos")
        var photos: ArrayList<Photos> = ArrayList()

        @SerializedName("place_id")
        var placeId: String? = null

        @SerializedName("scope")
        var scope: String? = null

        @SerializedName("alt_ids")
        var alternativeIds: ArrayList<AlternativeIds> = ArrayList()

        @SerializedName("rating")
        var rating: Double? = null

        @SerializedName("reference")
        var reference: String? = null

        @SerializedName("types")
        var types: Array<String>? = null

        @SerializedName("user_ratings_total")
        var userRatingsTotal: String? = null
    }

    inner class Geometry : Serializable {
        @SerializedName("location")
        var location: Location? = null

        @SerializedName("viewport")
        var viewport: Viewport? = null
    }

    inner class Location : Serializable {
        @SerializedName("lat")
        var lat: Double? = null

        @SerializedName("lng")
        var lng: Double? = null
    }

    inner class Viewport : Serializable {
        @SerializedName("northeast")
        var northEast: Location? = null

        @SerializedName("southwest")
        var southWest: Location? = null
    }

    inner class OpeningHours : Serializable {
        @SerializedName("open_now")
        var openNow: Boolean? = null
    }

    inner class Photos : Serializable {
        @SerializedName("height")
        var height: Int? = null

        @SerializedName("html_attributions")
        var htmlAttributions: Array<String>? = null

        @SerializedName("photo_reference")
        var photoReference: String? = null

        @SerializedName("width")
        var width: Int? = null
    }

    inner class AlternativeIds : Serializable {
        @SerializedName("place_id")
        var placeId: String? = null

        @SerializedName("scope")
        var scope: String? = null
    }
}
