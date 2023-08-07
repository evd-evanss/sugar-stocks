package com.sugarspoon.data.response

import java.io.Serializable

data class BrapiResponse(
    val indexes: List<Index>,
    val stocks: List<Stock>
)

data class QuotesResponse(
    val requestedAt: String,
    val results: List<StockDetailDto>,
): Serializable