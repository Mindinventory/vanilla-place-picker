package com.vanillaplacepicker.data

import com.google.gson.annotations.SerializedName

class AutocompletePredictionResponse {
    @SerializedName("status")
    var status: String? = null
    @SerializedName("predictions")
    var predictions: ArrayList<PredictionsBean>? = null
    @SerializedName("error_message")
    var errorMessage: String? = null

    class PredictionsBean {
        @SerializedName("description")
        var description: String? = null

        @SerializedName("id")
        var id: String? = null

        @SerializedName("place_id")
        var placeId: String? = null

        @SerializedName("reference")
        var reference: String? = null

        @SerializedName("structured_formatting")
        var structuredFormatting: StructuredFormattingBean? = null

        @SerializedName("matched_substrings")
        var matchedSubstrings: List<MatchedSubstringsBean>? = null

        @SerializedName("terms")
        var terms: List<TermsBean>? = null

        @SerializedName("types")
        var types: List<String>? = null

        class StructuredFormattingBean {
            @SerializedName("main_text")
            var mainText: String? = null

            @SerializedName("secondary_text")
            var secondaryText: String? = null

            @SerializedName("main_text_matched_substrings")
            var mainTextMatchedSubstrings: List<MainTextMatchedSubstringsBean>? = null

            class MainTextMatchedSubstringsBean {
                @SerializedName("length")
                var length: Int = 0

                @SerializedName("offset")
                var offset: Int = 0
            }
        }

        class MatchedSubstringsBean {
            @SerializedName("length")
            var length: Int = 0
            @SerializedName("offset")
            var offset: Int = 0
        }

        class TermsBean {
            @SerializedName("offset")
            var offset: Int = 0
            @SerializedName("value")
            var value: String? = null
        }
    }
}
