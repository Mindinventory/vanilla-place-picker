package com.vanillaplacepicker.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPrefs(context: Context) {
    private val sharedPreferences: SharedPreferences

    var deviceLatitude: Float
        set(value) = put(PREF_DEVICE_LATITUDE, value)
        get() = get(PREF_DEVICE_LATITUDE, Float::class.java)

    var deviceLongitude: Float
        set(value) = put(PREF_DEVICE_LONGITUDE, value)
        get() = get(PREF_DEVICE_LONGITUDE, Float::class.java)

    init {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    @Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
    private fun <T> get(key: String, clazz: Class<T>): T =
            when (clazz) {
                String::class.java -> sharedPreferences.getString(key, "")
                Boolean::class.java -> sharedPreferences.getBoolean(key, false)
                Float::class.java -> sharedPreferences.getFloat(key, 0f)
                Double::class.java -> sharedPreferences.getFloat(key, 0f)
                Int::class.java -> sharedPreferences.getInt(key, -1)
                Long::class.java -> sharedPreferences.getLong(key, -1L)
                else -> null
            } as T

    private fun <T> put(key: String, data: T) {
        val editor = sharedPreferences.edit()
        when (data) {
            is String -> editor.putString(key, data)
            is Boolean -> editor.putBoolean(key, data)
            is Float -> editor.putFloat(key, data)
            is Double -> editor.putFloat(key, data.toFloat())
            is Int -> editor.putInt(key, data)
            is Long -> editor.putLong(key, data)
        }
        editor.apply()
    }

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }

    companion object {
        const val PREFS_NAME = "KFCSharedPreferences"
        private const val PREFIX = "kfc_"
        const val PREF_SESSION_ACCESS_TOKEN = PREFIX + "access_token"
        const val PREF_DEVICE_LATITUDE = PREFIX + "device_latitude"
        const val PREF_DEVICE_LONGITUDE = PREFIX + "device_longitude"
    }
}