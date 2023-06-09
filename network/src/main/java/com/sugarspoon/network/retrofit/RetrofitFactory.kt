package com.sugarspoon.network.retrofit

import android.os.Build
import android.util.Base64
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {

    inline fun <reified Service> newInstance(okHttpClient: OkHttpClient, baseUrl: String): Service {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()
            .create(Service::class.java)
    }
}