package com.sugarspoon.data.api

import com.sugarspoon.data.response.BrapiResponse
import com.sugarspoon.data.response.InflationDTO
import com.sugarspoon.data.response.QuotesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiBrapi {

    @GET("/api/quote/list")
    suspend fun getQuoteList(): BrapiResponse

    @GET("/api/quote/{tickers}")
    suspend fun getDetails(
        @Path("tickers") tickers: String,
        @Query("range") range: String = "",
        @Query("interval") interval: String = "",
    ): QuotesResponse

    @GET("/api/v2/inflation")
    suspend fun getInflation(
        @Query(value = "country") country: String,
        @Query(value = "historical") historical: Boolean,
        @Query(value = "start") start: String,
        @Query(value = "end") end: String,
        @Query(value = "sortOrder") sortOrder: String = "asc",
    ): InflationDTO
}