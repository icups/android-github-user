package com.github.ext.gson

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

fun String.toRequestBody(): RequestBody {
    return this.toRequestBody("text/plain".toMediaTypeOrNull())
}

fun requestBodyImage(fieldName: String, fileName: String, byteArray: ByteArray): MultipartBody.Part {
    return MultipartBody.Part.createFormData(fieldName, fileName, byteArray.toRequestBody("image/jpeg".toMediaTypeOrNull()))
}