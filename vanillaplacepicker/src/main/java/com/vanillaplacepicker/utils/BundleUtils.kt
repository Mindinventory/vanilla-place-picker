package com.vanillaplacepicker.utils

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle

object BundleUtils {
    // Get MetaData from AndroidManifest file of given context
    fun getMetaData(context: Context): Bundle {
        return context.packageManager.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA).metaData
    }
}