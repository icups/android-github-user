package com.github.response

import com.squareup.moshi.Json
import java.io.Serializable

data class BaseResponse<T>(
    val page: Int = 0,
    @Json(name = "total_results") val totalResult: Int = 0,
    @Json(name = "total_pages") val totalPages: Int = 0,
    @Json(name = "results") val result: T
) : Serializable