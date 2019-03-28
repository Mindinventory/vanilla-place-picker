package com.vanillaplacepicker.utils

object KeyUtils {
    const val SUCCESS_RESULT = 0
    const val FAILURE_RESULT = 1
    private const val PACKAGE_NAME = "com.google.android.gms.location.sample.locationaddress"
    const val RECEIVER = "$PACKAGE_NAME.RECEIVER"
    const val RESULT_DATA_KEY = "$PACKAGE_NAME.RESULT_DATA_KEY"
    const val RESULT_MESSAGE_KEY = "$PACKAGE_NAME.RESULT_MESSAGE_KEY"
    const val LOCATION_DATA_EXTRA = "$PACKAGE_NAME.LOCATION_DATA_EXTRA"
    const val DEFAULT_ZOOM_LEVEL = 18f
    const val GOOGLE_MAP_CAMERA_ANIMATE_DURATION = 2000
    const val REQUEST_CHECK_SETTINGS = 101
    const val REQUEST_PERMISSIONS_REQUEST_CODE = 102
    const val LOCATION_UPDATE_INTERVAL = 3000L
    const val DEBOUNCE_INTERVAL = 300L

    const val REQUEST_PLACE_PICKER = 100
    const val SELECTED_PLACE = "place"

    const val API_KEY = "key"
    const val QUERY = "query"
    const val REGION = "region"
    const val LATITUDE = "latitude"
    const val LONGITUDE = "longitude"
    const val LOCATION = "location"
    const val RADIUS = "radius"
    const val LANGUAGE = "language"
    const val MIN_PRICE = "minprice"
    const val MAX_PRICE = "maxprice"
    const val OPEN_NOW = "opennow"
    const val PAGE_TOKEN = "pagetoken"
    const val TYPES = "types"
    const val TINT_COLOR = "tintColor"
    const val MIN_CHAR_LIMIT = "minCharLimit"
    const val ZONE_LOCALE = "zoneLocale"
    const val ZONE_RECT = "zoneRect"
    const val ZONE_DEFAULT_LOCALE = "zoneDefaultLocale"
    const val ENABLE_SATELLITE_VIEW = "enableSatelliteView"
    const val GOOGLE_TIMEZONE_ENABLED = "googleTimeZoneEnabled"
    const val MAP_STYLE_JSON_RES_ID = "mapStyleJSONResId"
    const val MAP_STYLE_STRING = "mapStyleString"
    const val MAP_PIN_DRAWABLE = "mapPinDrawable"

    // status
    const val OK = "OK"
    const val ZERO_RESULTS = "ZERO_RESULTS"
    const val OVER_QUERY_LIMIT = "OVER_QUERY_LIMIT"
}