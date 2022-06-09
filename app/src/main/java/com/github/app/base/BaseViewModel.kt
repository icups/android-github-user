package com.github.app.base

import android.content.Context
import androidx.lifecycle.ViewModel
import com.bumptech.glide.load.HttpException
import com.github.app.shared.ResourceProvider
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BaseViewModel : ViewModel() {

    fun requireErrorMessage(context: Context, exception: Exception): String {
        val provider = ResourceProvider(context)
        exception.printStackTrace()

        return when (exception) {
            is SocketTimeoutException -> provider.timeout
            is HttpException -> provider.generalError
            is UnknownHostException -> provider.networkError
            else -> exception.message.orEmpty()
        }
    }

}