package com.sugarspoon.data.sources

import com.sugarspoon.data.api.ApiFounds
import com.sugarspoon.data.request.CredentialsRequest
import com.sugarspoon.data.request.FoundsRequest
import com.sugarspoon.network.BuildConfig
import com.sugarspoon.network.interceptors.InterceptRequest
import com.sugarspoon.network.interceptors.LoginRequestInterceptor
import com.sugarspoon.network.retrofit.RetrofitFactory
import javax.inject.Inject
import kotlinx.coroutines.flow.flow
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class FoundsDataSource @Inject constructor(interceptRequest: InterceptRequest) {

    val prodUrl = "https://api.anbima.com.br"

    private val serviceDebug = RetrofitFactory().newInstance<ApiFounds>(
        okHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(interceptRequest)
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build(),
        baseUrl = BuildConfig.SERVER_URL
    )

    private val serviceProd = RetrofitFactory().newInstance<ApiFounds>(
        okHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(LoginRequestInterceptor())
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build(),
        baseUrl = prodUrl
    )

    fun refreshToken(request: CredentialsRequest) = flow {
        try {
            emit(serviceProd.getCredentials(request = request))
        } catch (e: Exception) {
            throw e
        }
    }

    fun getFound(request: FoundsRequest) = flow {
        try {
            emit(
                serviceDebug.getFounds(
                    page = request.page,
                    size = request.size
                ).content.filter { it.type == "Ações" && it.status == "A" }
            )
        } catch (e: Exception) {
            throw e
        }
    }

    fun getFoundDetails(code: Int) = flow {
        try {
            emit(
                serviceDebug.getDetails(code).first()
            )
        } catch (e: Exception) {
            throw e
        }
    }

}