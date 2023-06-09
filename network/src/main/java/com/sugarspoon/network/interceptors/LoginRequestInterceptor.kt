package com.sugarspoon.network.interceptors

import android.os.Build
import android.util.Base64
import java.io.IOException
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

private const val authorization = "MmsTd7wfrnEk:6kq3CJNJJcLP"

class LoginRequestInterceptor  : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder: Request.Builder = chain.request().newBuilder()

        //adding a header to the original request
        requestBuilder
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "Basic TW1zVGQ3d2ZybkVrOjZrcTNDSk5KSmNMUA==")

        val response = chain.proceed(requestBuilder.build())

        //returns a response
        return response
    }

    private fun String.getBase64() : String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        "Basic ${java.util.Base64.getMimeDecoder().decode(this)}"
    } else {
        String(android.util.Base64.decode(this, Base64.DEFAULT)) // Unresolved reference: decode
    }
}