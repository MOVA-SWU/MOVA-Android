package com.example.mova.ui.extensions

import android.content.Context
import android.util.TypedValue

fun Context.dpToPxExtensions(dp: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        resources.displayMetrics
    ).toInt()
}