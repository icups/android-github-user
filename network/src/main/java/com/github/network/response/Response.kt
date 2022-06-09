package com.github.network.response

import android.os.Parcelable
import com.github.network.model.State
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Response<T : Parcelable?>(
    val errorCode: Int,
    val errorMessage: String? = null,
    @SerializedName("payload") val data: T? = null
) : Parcelable {

    private fun isSuccess(): Boolean = errorCode == 0

    private fun requireErrorMessage(): String {
        return when (errorCode) {
            1043 -> "Invalid email address, e.g. info@github.com"
            1406 -> "Name is too long, maximum 100 characters"
            4417 -> "Invalid verification code"
            else -> errorMessage ?: ""
        }
    }

    fun <T> requireState(data: T? = null, action: ((T) -> Unit)? = null): State<T> {
        return if (isSuccess()) {
            data?.run { action?.invoke(this) }
            State.success(data)
        } else {
            State.error(requireErrorMessage(), errorCode = errorCode)
        }
    }

}