package com.github.exception

import com.github.response.ErrorResponse
import java.io.IOException

class NetworkException(private val error: ErrorResponse?) : IOException() {

    override val message: String?
        get() = error?.message

}