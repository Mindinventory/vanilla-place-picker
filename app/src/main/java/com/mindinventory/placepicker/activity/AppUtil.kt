package com.mindinventory.placepicker.activity

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.core.view.isGone

object AppUtil {

    const val STATUS_BAR_TAG = "status_bar"

    // customize as per your requirement ie. statusBarColor instead of drawable, view instead of activity.

    fun Activity.applyEdgeToEdgeInsets(statusBarDrawable: Drawable?) {
        // Add this condition if you only want to support edge to edge in Android 15+ devices else remove this.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
            val view = findViewById<View>(android.R.id.content)
            ViewCompat.setOnApplyWindowInsetsListener(view) { v, windowInsets ->
                val bars = windowInsets.getInsets(
                    WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout() or WindowInsetsCompat.Type.ime()
                )

                val statusBarHeight =
                    windowInsets.getInsets(WindowInsetsCompat.Type.statusBars()).top
                applyStatusBarColor(window, statusBarDrawable, true, statusBarHeight)
                WindowCompat.getInsetsController(
                    window,
                    window.decorView
                ).isAppearanceLightStatusBars = true
                WindowCompat.getInsetsController(
                    window,
                    window.decorView
                ).isAppearanceLightNavigationBars = true
                v.updatePadding(
                    left = bars.left,
                    top = bars.top,
                    right = bars.right,
                    bottom = bars.bottom,
                )
                windowInsets
            }
        }
    }

    fun applyStatusBarColor(
        window: Window,
        statusBarBackground: Drawable?,
        isDecor: Boolean,
        height: Int,
    ): View {
        val parent =
            if (isDecor) window.decorView as ViewGroup else (window.findViewById<View>(android.R.id.content) as ViewGroup)
        var fakeStatusBarView = parent.findViewWithTag<View>(STATUS_BAR_TAG)
        Log.e("VanilaIceCream", "statusbar View = ${fakeStatusBarView != null}")
        if (fakeStatusBarView != null) {
            if (fakeStatusBarView.isGone) {
                fakeStatusBarView.visibility = View.VISIBLE
            }
            fakeStatusBarView.background = statusBarBackground
        } else {
            fakeStatusBarView = createStatusBarView(window.context, statusBarBackground, height)
            parent.addView(fakeStatusBarView)
        }
        return fakeStatusBarView
    }

    private fun createStatusBarView(
        context: Context,
        statusBarBackground: Drawable?,
        height: Int,
    ): View {
        val statusBarView = View(context)
        statusBarView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, height
        )
        statusBarView.background = statusBarBackground
        statusBarView.tag = STATUS_BAR_TAG
        return statusBarView
    }

    fun Context.getActionBarHeight(): Int {
        val styledAttributes =
            theme.obtainStyledAttributes(intArrayOf(android.R.attr.actionBarSize))
        val actionBarHeight = styledAttributes.getDimensionPixelSize(0, 0)
        styledAttributes.recycle()
        return actionBarHeight
    }

    fun Activity.adjustContentBelowActionBar(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
            val actionBarHeight = this.getActionBarHeight()
            view.setPadding(
                view.paddingLeft,
                actionBarHeight,
                view.paddingRight,
                view.paddingBottom
            )
        }
    }
}