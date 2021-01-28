package com.github.ext.type

import android.text.Editable

fun Editable.toInt(): Int {
    return if (toString().isEmpty()) {
        0
    } else {
        toString().replace(".", "").toInt()
    }
}

fun Editable.toFloat(): Float {
    return if (toString().isEmpty()) {
        0f
    } else {
        toString().replace(",", "").toFloat()
    }
}

fun Editable.getValue(position: Int? = null): String {
    return if (position == null)
        toString()
    else
        toString().toCharArray()[position].toString()
}

fun Editable.getValueRangeOf(beginIndex: Int, endIndex: Int): String {
    return substring(beginIndex, endIndex)
}