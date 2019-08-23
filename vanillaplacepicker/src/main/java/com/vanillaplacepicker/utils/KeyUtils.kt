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
    const val DEFAULT_LOCATION = 0.0
    const val DEFAULT_STYLE_JSON_RESID = 0
    const val DEFAULT_FETCH_LOCATION_INTERVAL = 5000L

    const val REQUEST_PLACE_PICKER = 100
    const val SELECTED_PLACE = "place"

    const val EXTRA_CONFIG = "extraConfig"
}