package com.sugarspoon.domain.repositories

import com.sugarspoon.domain.model.remote.ApiDataDto
import com.sugarspoon.domain.model.remote.QuotesResponseDto

interface ApiRepository {
    suspend fun getApiData(): Result<ApiDataDto>
    suspend fun getDetail(
        tickers: String,
        range: String = "",
        interval: String = "",
    ): Result<QuotesResponseDto>
}