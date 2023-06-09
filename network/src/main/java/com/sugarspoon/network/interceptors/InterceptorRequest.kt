package com.sugarspoon.network.interceptors

import android.os.Build
import android.util.Base64
import java.io.IOException
import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

private const val authorization = "MmsTd7wfrnEk:6kq3CJNJJcLP"

class InterceptRequest @Inject constructor(
    val sessionManager: SessionManagerImpl
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder: Request.Builder = chain.request().newBuilder()

        //adding a header to the original request
        requestBuilder
            .addHeader("Content-Type", "application/json")

        sessionManager.fetchAuthToken()?.let {
            requestBuilder.addHeader("client_id", "MmsTd7wfrnEk")
            requestBuilder.addHeader("access_token", "$it")
        }

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