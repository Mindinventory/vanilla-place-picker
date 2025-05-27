package com.mindinventory.placepicker.activity

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.mindinventory.placepicker.R
import com.mindinventory.placepicker.activity.AppUtil.applyEdgeToEdgeInsets

class VanillaApp : Application() {
    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycle()
    }

    private fun registerActivityLifecycle() {
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                activity.applyEdgeToEdgeInsets(
                    ContextCompat.getDrawable(activity, R.drawable.status_bar_background)
                )
            }

            override fun onActivityStarted(activity: Activity) {}

            override fun onActivityResumed(activity: Activity) {}

            override fun onActivityPaused(activity: Activity) {}

            override fun onActivityStopped(activity: Activity) {}

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

            override fun onActivityDestroyed(activity: Activity) {}

        })
    }
}