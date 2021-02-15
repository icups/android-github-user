package com.github.response

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class ErrorResponse(
    val message: String = "",
    @Json(name = "documentation_url") val url: String = ""
) : Parcelable