package com.vanillaplacepicker.extenstion

fun String?.isRequiredField(): Boolean {
    return this != null && isNotEmpty() && isNotBlank()
}

