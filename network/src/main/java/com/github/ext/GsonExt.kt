package com.github.ext

import com.squareup.moshi.Moshi

private val builder: Moshi = Moshi.Builder().build()

fun <T> fromJson(json: String?, classOf: Class<T>): T? {
    return builder.adapter(classOf).fromJson(json.orEmpty())
}

fun <T> T.toJson(classOf: Class<T>): String {
    return builder.adapter(classOf).toJson(this)
}