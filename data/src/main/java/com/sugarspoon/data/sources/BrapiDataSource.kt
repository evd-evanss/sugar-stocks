package com.sugarspoon.data.sources

import com.sugarspoon.data.api.ApiBrapi
import com.sugarspoon.data.retrofit.RetrofitFactory
import javax.inject.Inject
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class BrapiDataSource @Inject constructor() {

    private val prodUrl = "https://brapi.dev"

    private val serviceProd = RetrofitFactory().newInstance<ApiBrapi>(
        okHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build(),
        baseUrl = prodUrl
    )

    suspend fun getQuoteList() = serviceProd.getQuoteList()

    suspend fun getDetails(
         tickers: String,
         range: String,
         interval: String,
    ) = serviceProd.getDetails(tickers, range, interval)

    suspend fun getInflation(
        country: String,
        historical: Boolean,
        start: String,
        end: String
    ) = serviceProd.getInflation(
        country,
        historical,
        start,
        end
    )
}