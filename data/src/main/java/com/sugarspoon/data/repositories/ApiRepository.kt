package com.sugarspoon.data.repositories

import com.sugarspoon.data.response.BrapiResponse
import com.sugarspoon.data.response.InflationDTO
import com.sugarspoon.data.response.MarketStatus
import com.sugarspoon.data.response.QuotesResponse
import kotlinx.coroutines.flow.Flow

interface ApiRepository {
    fun getQuoteList(): Flow<BrapiResponse>
    fun getDetail(
        tickers: String,
        range: String = "",
        interval: String = "",
    ): Flow<QuotesResponse>
    fun getMarketStatus(): Flow<MarketStatus>
    fun getInflation(
        country: String,
        historical: Boolean = false,
        start: String = "",
        end: String = ""
    ): Flow<InflationDTO>
}