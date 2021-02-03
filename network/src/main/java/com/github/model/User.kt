package com.github.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
class User(
    val id: Int = 1,
    val type: String = "",
    @Json(name = "login") val username: String = "",
    @Json(name = "avatar_url") val avatarUrl: String = "",
    @Json(name = "html_url") val url: String = ""
) : Parcelable