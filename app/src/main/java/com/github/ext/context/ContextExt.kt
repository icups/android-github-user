package com.github.ext.context

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

@SuppressWarnings("unused")
fun Context.getScreenWidth(): Int {
    val displayMetrics = DisplayMetrics()
    val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    windowManager.defaultDisplay.getMetrics(displayMetrics)

    return displayMetrics.widthPixels
}