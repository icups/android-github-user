package com.github.ext.fragment

import androidx.fragment.app.Fragment

fun Fragment.getStringArgs(key: String): String {
    arguments?.apply { getString(key)?.let { data -> return data } }
    return ""
}

fun Fragment.getIntArgs(key: String): Int {
    arguments?.apply { getInt(key).let { data -> return data } }
    return 0
}