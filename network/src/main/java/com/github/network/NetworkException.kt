package com.github.network

import android.content.Context
import java.io.IOException

class NetworkException(private val context: Context) : IOException() {

    override val message: String
        get() = context.getString(R.string.no_internet_connection)

}