package com.vanillaplacepicker.extenstion

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity

/**
 * this function will help to open app setting using Intent.
 */
fun AppCompatActivity.openAppSetting() {
    val intent = Intent()
    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    val uri = Uri.fromParts("package", packageName, null)
    intent.data = uri
    startActivity(intent)
}

fun AppCompatActivity.hasExtra(key: String): Boolean {
    return this.intent.hasExtra(key)
}