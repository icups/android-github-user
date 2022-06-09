package com.github.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Request(
    val module: String,
    val action: String,
    val token: String? = null
) : Parcelable