package com.vanillaplacepicker.extenstion

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.vanillaplacepicker.R

fun AppCompatActivity.showAlertDialog(
        message: Int = 0, title: Int = 0,
        positiveBtnText: Int = R.string.ok,
        negativeBtnText: Int = R.string.cancel,
        accepted: () -> Unit = {},
        rejected: () -> Unit = {}
) {
    AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveBtnText) { dialog, _ ->
                accepted.invoke()
                dialog.dismiss()
            }.setNegativeButton(negativeBtnText) { dialog, _ ->
                rejected.invoke()
                dialog.dismiss()
            }.show()
}