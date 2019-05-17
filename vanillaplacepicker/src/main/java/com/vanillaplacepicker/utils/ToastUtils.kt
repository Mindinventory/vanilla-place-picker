package com.vanillaplacepicker.utils

import android.content.Context
import android.widget.Toast

object ToastUtils {
    private lateinit var toast: Toast

    fun showToast(context: Context, message: String?, duration: ToastLength = ToastLength.Long) {
        if (::toast.isInitialized)
            toast.cancel()
        message?.let {
            toast = Toast.makeText(context, message, duration.value)
            toast.show()
        }
    }

    fun showToast(context: Context, message: Int, duration: ToastLength = ToastLength.Long) {
        if (::toast.isInitialized) toast.cancel()
        toast = Toast.makeText(context, message, duration.value)
        toast.show()
    }
}