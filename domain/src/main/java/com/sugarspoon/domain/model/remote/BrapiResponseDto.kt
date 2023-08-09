package com.sugarspoon.domain.model.remote

import java.io.Serializable

data class BrapiResponseDto(
    val indexes: List<IndexDto>,
    val stocks: List<StockDto>
)

data class QuotesResponseDto(
    val requestedAt: String,
    val results: List<StockDetailDto>,
): Serializable