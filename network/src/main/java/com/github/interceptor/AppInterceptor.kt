package com.github.interceptor

import com.github.exception.NetworkException
import com.github.ext.fromJson
import com.github.response.ErrorResponse
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AppInterceptor(private var accessToken: String? = null) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val newRequest = request.newBuilder().apply {
            addHeader("Accept", "application/json")
            accessToken?.run { addHeader("Authorization", "Bearer $this") }
        }.build()

        val response = chain.proceed(newRequest)

        if (response.isSuccessful) return response
        else throw NetworkException(getErrorResponse(response))
    }

    private fun getErrorResponse(response: Response): ErrorResponse? {
        return fromJson(response.body?.string(), ErrorResponse::class.java)
    }

}