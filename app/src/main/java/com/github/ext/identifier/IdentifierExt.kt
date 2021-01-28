package com.github.ext.identifier

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.provider.Settings.Secure
import androidx.fragment.app.Fragment
import java.util.*


@SuppressLint("HardwareIds")
fun Fragment.getDeviceID(): String {
    return Secure.getString(requireContext().contentResolver, Secure.ANDROID_ID)
}

@SuppressLint("HardwareIds")
fun Fragment.getAppVersion(): String {
    try {
        requireContext().packageManager.getPackageInfo(requireContext().packageName, 0).apply {
            return versionName
        }
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }

    return "1.0"
}

fun getDeviceVersion(): String {
    return android.os.Build.VERSION.RELEASE
}

fun getDeviceModel(): String {
    return android.os.Build.MODEL
}

fun getTimeZone(): String {
    return TimeZone.getDefault().getDisplayName(false, TimeZone.SHORT)
}