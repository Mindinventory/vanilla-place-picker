package com.vanillaplacepicker.extenstion

import android.view.View
import androidx.core.view.isVisible

fun View.hideView() {
    this.isVisible = false
}

fun View.showView() {
    this.isVisible = true
}