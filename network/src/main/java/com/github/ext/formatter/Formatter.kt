package com.github.ext.formatter

import com.github.ext.common.applyIf
import java.text.SimpleDateFormat
import java.util.*

fun Date?.formatDate(toPattern: String = "MMMM dd, yyyy", utc: Boolean = true): String {
    return if (this == null) "" else try {
        val formatter = SimpleDateFormat(toPattern, Locale.getDefault()).applyIf(utc) { timeZone = TimeZone.getTimeZone("UTC") }
        formatter.format(this)
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

fun String?.formatDate(fromPattern: String = "yyyy-MM-dd hh:mm:ss", toPattern: String = "MMMM dd, yyyy", utc: Boolean = true): String {
    return if (isNullOrEmpty()) "" else try {
        var formatter = SimpleDateFormat(fromPattern, Locale.getDefault()).applyIf(utc) { timeZone = TimeZone.getTimeZone("UTC") }
        val date: Date? = formatter.parse(this)

        formatter = SimpleDateFormat(toPattern, Locale.getDefault())
        date?.run { formatter.format(this) }.orEmpty()
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}