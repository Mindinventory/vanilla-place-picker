package com.vanillaplacepicker.presentation.builder

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.vanillaplacepicker.extenstion.isRequiredField
import com.vanillaplacepicker.presentation.autocomplete.VanillaAutocompleteActivity
import com.vanillaplacepicker.presentation.map.VanillaMapActivity
import com.vanillaplacepicker.utils.*
import wrap

class VanillaPlacePicker {

    companion object {
        val TAG = VanillaPlacePicker::class.java.simpleName
    }

    class Builder(private val context: Context) {
        private val vanillaConfig = VanillaConfig()

        /**
         * To enable map view with place picker
         */
        fun with(pickerType: PickerType): Builder {
            vanillaConfig.pickerType = pickerType
            return this
        }

        /**
         * When this parameter is used,
         * the country name is omitted from the resulting formatted_address for results in the specified region.
         */
        fun setRegion(region: String): Builder {
            vanillaConfig.region = region
            return this
        }

        /**
         * Request with default latitude & longitude for near by places
         */
        fun withLocation(latitude: Double, longitude: Double): Builder {
            vanillaConfig.latitude = latitude
            vanillaConfig.longitude = longitude
            return this
        }

        /**
         * Defines the distance (in meters) within which to bias place results.
         */
        fun setRadius(radius: Int): Builder {
            vanillaConfig.radius = radius
            return this
        }

        /**
         * Set default langauge for results
         */
        fun setLanguage(language: String): Builder {
            vanillaConfig.language = language
            return this
        }

        /**
         * Restricts results to only those places within the specified price level.
         * Valid values are in the range from 0 (most affordable) to 4 (most expensive), inclusive.
         */
        fun setMinPrice(minPrice: Int): Builder {
            vanillaConfig.minPrice = minPrice
            return this
        }

        /**
         * Restricts results to only those places within the specified price level.
         * Valid values are in the range from 0 (most affordable) to 4 (most expensive), inclusive.
         */
        fun setMaxPrice(maxPrice: Int): Builder {
            vanillaConfig.maxPrice = maxPrice
            return this
        }

        /**
         * Returns only those places that are open for business at the time the query is sent.
         */
        fun isOpenNow(openNow: Boolean): Builder {
            vanillaConfig.isOpenNow = openNow
            return this
        }

        fun setPageToken(pageToken: String): Builder {
            vanillaConfig.pageToken = pageToken
            return this
        }

        fun setTypes(types: String): Builder {
            vanillaConfig.types = types
            return this
        }

        /**
         * Apply Tint color to Back, Clear button of PlacePicker AutoComplete header UI
         */
        fun setTintColor(colorResourceId: Int): Builder {
            vanillaConfig.tintColor = colorResourceId
            return this
        }

        /**
         * Restrict user input limit to minimum char
         */
        fun setMinCharLimit(minCharLimit: Int): Builder {
            vanillaConfig.minCharLimit = minCharLimit
            return this
        }

        /**
         * To add zone locale
         */
        fun zoneLocale(zoneLocale: String): Builder {
            vanillaConfig.zoneLocale = zoneLocale
            return this
        }

        /**
         * To restrict request zone by rect
         */
        fun zoneRect(zoneRect: SearchZoneRect): Builder {
            vanillaConfig.zoneRect = zoneRect
            return this
        }

        fun zoneDefaultLocale(zoneDefaultLocale: Boolean): Builder {
            vanillaConfig.zoneDefaultLocale = zoneDefaultLocale
            return this
        }

        /**
         * To enable satellite view in map
         */
        fun enableSatelliteView(enableSatelliteView: Boolean): Builder {
            vanillaConfig.enableSatelliteView = enableSatelliteView
            return this
        }

        /**
         * Add a map with custom styling
         * With style options you can customize the presentation of the standard Google map styles,
         * changing the visual display of features like roads, parks, businesses, and other points of interest.
         * Add a resource containing a JSON style object (Use a raw resource)
         * */
        fun setMapStyle(jsonResourceIdMapStyle: Int): Builder {
            vanillaConfig.mapStyleJSONResId = jsonResourceIdMapStyle
            return this
        }

        /**
         * Add a map with custom styling
         * With style options you can customize the presentation of the standard Google map styles,
         * changing the visual display of features like roads, parks, businesses, and other points of interest.
         * Add a resource containing a JSON style object (Use a string resource)
         * */
        fun setMapType(mapType: MapType): Builder {
            vanillaConfig.mapType = mapType
            return this
        }

        /**
         * Set custom Map Pin image
         * */
        fun setMapPinDrawable(mapPinDrawableResId: Int): Builder {
            vanillaConfig.mapPinDrawable = mapPinDrawableResId
            return this
        }

        /**
         * Set picker language
         */
        fun setPickerLanguage(pickerLanguage: PickerLanguage): Builder {
            ContextWrapper(context).wrap(pickerLanguage.value)
            return this
        }

        /**
         * Set custom Place holder for autcomplete view
         * */
        fun setAutoCompletePlaceHolder(placeholderDrawableResId: Int): Builder {
            vanillaConfig.autoCompletePlaceHolder = placeholderDrawableResId
            return this
        }

        /**
         * Get Google Places API key
         */
        private fun getApiKey(): String {
            val metadataBundle: Bundle? = BundleUtils.getMetaData(context)
            if (metadataBundle != null) {
                return if (metadataBundle.getString("com.google.android.geo.API_KEY").isRequiredField())
                    metadataBundle.getString("com.google.android.geo.API_KEY")!!
                else {
                    Log.e(
                        TAG,
                        "Couldn't get Google API key from application meta data. Was it set in your AndroidManifest.xml?"
                    )
                    ""
                }
            } else {
                Log.e(
                    TAG,
                    "Couldn't get Google API key from application meta data. Was it set in your AndroidManifest.xml?"
                )
                return ""
            }
        }

        fun build(): Intent {
            val intent = if (vanillaConfig.pickerType == PickerType.AUTO_COMPLETE) {
                Intent(context, VanillaAutocompleteActivity::class.java)
            } else {
                Intent(context, VanillaMapActivity::class.java)
            }
            vanillaConfig.apiKey = getApiKey()
            intent.putExtra(KeyUtils.EXTRA_CONFIG, vanillaConfig)
            return intent
        }
    }
}