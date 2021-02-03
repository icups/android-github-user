package com.github.model

import com.github.exception.NetworkException

sealed class State<out T> {
    data class Success<out T>(val data: T) : State<T>()

    data class Failure(val error: NetworkException? = null) : State<Nothing>() {
        val message get() = error?.message

        init {
            error?.printStackTrace()
        }
    }

    data class Error(val message: String?) : State<Nothing>()
}