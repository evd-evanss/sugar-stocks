package com.sugarspoon.domain.repositories

import com.sugarspoon.domain.model.remote.BrapiResponseDto
import com.sugarspoon.domain.model.remote.InflationResponseDTO
import com.sugarspoon.domain.model.remote.MarketStatusDto
import com.sugarspoon.domain.model.remote.QuotesResponseDto
import kotlinx.coroutines.flow.Flow

interface ApiRepository {
    fun getQuoteList(): Flow<BrapiResponseDto>
    fun getDetail(
        tickers: String,
        range: String = "",
        interval: String = "",
    ): Flow<QuotesResponseDto>
    fun getMarketStatus(): Flow<MarketStatusDto>
    fun getInflation(
        country: String,
        historical: Boolean = false,
        start: String = "",
        end: String = ""
    ): Flow<InflationResponseDTO>
}