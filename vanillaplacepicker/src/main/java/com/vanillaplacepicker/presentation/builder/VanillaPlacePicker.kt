package com.vanillaplacepicker.presentation.builder

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.vanillaplacepicker.extenstion.isRequiredField
import com.vanillaplacepicker.presentation.autocomplete.VanillaAutocompleteActivityVanilla
import com.vanillaplacepicker.presentation.map.VanillaMapActivityVanilla
import com.vanillaplacepicker.utils.BundleUtils
import com.vanillaplacepicker.utils.KeyUtils
import com.vanillaplacepicker.utils.Logger

class VanillaPlacePicker {

    companion object {
        val TAG = VanillaPlacePicker::class.java.simpleName
    }

    class Builder(
        val context: Context,
        val requestCode: Int
    ) {
        var isMapEnable: Boolean = false
        var region: String? = null
        var latitude: Double? = null
        var longitude: Double? = null
        var radius: Int? = null
        var language: String? = null
        var minPrice: Int? = null
        var maxPrice: Int? = null
        var isOpenNow: Boolean? = null
        var pageToken: String? = null
        var types: String? = null
        var tintColor: Int? = null
        var minCharLimit: Int? = null
        var zoneLocale: String? = null
        var zoneRect: SearchZoneRect? = null
        var zoneDefaultLocale = false
        var enableSatelliteView = true
        var googleTimeZoneEnabled = false
        var mapStyleJSONResId: Int? = null
        var mapStyleString: String? = null
        var mapPinDrawable: Int? = null

        fun enableMap(): Builder {
            this.isMapEnable = true
            return this
        }

        /*
        * When this parameter is used,
        * the country name is omitted from the resulting formatted_address for results in the specified region.
        * */
        fun setRegion(region: String): Builder {
            this.region = region
            return this
        }

        fun withLocation(latitude: Double, longitude: Double): Builder {
            this.latitude = latitude
            this.longitude = longitude
            return this
        }

        // Defines the distance (in meters) within which to bias place results.
        fun setRadius(radius: Int): Builder {
            this.radius = radius
            return this
        }

        fun setLanguage(language: String): Builder {
            this.language = language
            return this
        }

        /*
        * for MinPrice, MaxPrice
        * Restricts results to only those places within the specified price level.
        * Valid values are in the range from 0 (most affordable) to 4 (most expensive), inclusive.
        * */
        fun setMinPrice(minPrice: Int): Builder {
            this.minPrice = minPrice
            return this
        }

        fun setMaxPrice(maxPrice: Int): Builder {
            this.maxPrice = maxPrice
            return this
        }

        // Returns only those places that are open for business at the time the query is sent.
        fun isOpenNow(openNow: Boolean): Builder {
            this.isOpenNow = openNow
            return this
        }

        fun setPageToken(pageToken: String): Builder {
            this.pageToken = pageToken
            return this
        }

        fun setTypes(types: String): Builder {
            this.types = types
            return this
        }

        /*
        * Apply Tint color to Back, Clear button of PlacePicker AutoComplete header UI
        * */
        fun setTintColor(colorResourceId: Int): Builder {
            this.tintColor = colorResourceId
            return this
        }

        fun setMinCharLimit(minCharLimit: Int): Builder {
            this.minCharLimit = minCharLimit
            return this
        }

        fun zoneLocale(zoneLocale: String): Builder {
            this.zoneLocale = zoneLocale
            return this
        }

        fun zoneRect(zoneRect: SearchZoneRect): Builder {
            this.zoneRect = zoneRect
            return this
        }

        fun zoneDefaultLocale(zoneDefaultLocale: Boolean): Builder {
            this.zoneDefaultLocale = zoneDefaultLocale
            return this
        }

        fun enableSatelliteView(enableSatelliteView: Boolean): Builder {
            this.enableSatelliteView = enableSatelliteView
            return this
        }

        /*
        * Add a map with custom styling
        * With style options you can customize the presentation of the standard Google map styles,
        * changing the visual display of features like roads, parks, businesses, and other points of interest.
        * Add a resource containing a JSON style object (Use a raw resource)
        * */
        fun setMapStyle(jsonResourceIdMapStyle: Int): Builder {
            this.mapStyleJSONResId = jsonResourceIdMapStyle
            return this
        }

        /*
        * Add a map with custom styling
        * With style options you can customize the presentation of the standard Google map styles,
        * changing the visual display of features like roads, parks, businesses, and other points of interest.
        * Add a resource containing a JSON style object (Use a string resource)
        * */
        fun setMapStyle(stringMapStyle: String): Builder {
            this.mapStyleString = stringMapStyle
            return this
        }

        /*
        * Set custom Map Pin image
        * */
        fun setMapPinDrawable(mapPinDrawableResId: Int): Builder {
            this.mapPinDrawable = mapPinDrawableResId
            return this
        }

        fun build() {
            val intent = if (isMapEnable) {
                Intent(context, VanillaMapActivityVanilla::class.java)
            } else {
                Intent(context, VanillaAutocompleteActivityVanilla::class.java)
            }
            val metadataBundle: Bundle = BundleUtils.getMetaData(context)
            if(metadataBundle.getString("com.google.android.geo.API_KEY").isRequiredField())
                intent.putExtra(KeyUtils.API_KEY, metadataBundle.getString("com.google.android.geo.API_KEY"))
            else
                Logger.e(TAG, "Couldn't get Google API key from application meta data. Was it set in your AndroidManifest.xml?")
            region?.let {
                intent.putExtra(KeyUtils.REGION, it)
            }
            latitude?.let {
                intent.putExtra(KeyUtils.LATITUDE, it)
            }
            longitude?.let {
                intent.putExtra(KeyUtils.LONGITUDE, it)
            }
            radius?.let {
                intent.putExtra(KeyUtils.RADIUS, it)
            }
            language?.let {
                intent.putExtra(KeyUtils.LANGUAGE, it)
            }
            minPrice?.let {
                intent.putExtra(KeyUtils.MIN_PRICE, it)
            }
            maxPrice?.let {
                intent.putExtra(KeyUtils.MAX_PRICE, it)
            }
            isOpenNow?.let {
                intent.putExtra(KeyUtils.OPEN_NOW, it)
            }
            pageToken?.let {
                intent.putExtra(KeyUtils.PAGE_TOKEN, it)
            }
            types?.let {
                intent.putExtra(KeyUtils.TYPES, it)
            }
            tintColor?.let {
                intent.putExtra(KeyUtils.TINT_COLOR, it)
            }
            minCharLimit?.let {
                intent.putExtra(KeyUtils.MIN_CHAR_LIMIT, it)
            }
            mapStyleJSONResId?.let {
                intent.putExtra(KeyUtils.MAP_STYLE_JSON_RES_ID, it)
            }
            mapStyleString?.let {
                intent.putExtra(KeyUtils.MAP_STYLE_STRING, it)
            }
            mapPinDrawable?.let {
                intent.putExtra(KeyUtils.MAP_PIN_DRAWABLE, it)
            }
            intent.putExtra(KeyUtils.ZONE_LOCALE, zoneLocale)
            intent.putExtra(KeyUtils.ZONE_RECT, zoneRect)
            intent.putExtra(KeyUtils.ZONE_DEFAULT_LOCALE, zoneDefaultLocale)
            intent.putExtra(KeyUtils.ENABLE_SATELLITE_VIEW, enableSatelliteView)
            intent.putExtra(KeyUtils.GOOGLE_TIMEZONE_ENABLED, googleTimeZoneEnabled)
            (context as Activity).startActivityForResult(intent, requestCode)
        }
    }
}