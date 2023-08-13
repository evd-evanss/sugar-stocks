package com.sugarspoon.pocketstocks.ui.home

import com.sugarspoon.pocketstocks.base.UiStates
import com.sugarspoon.pocketstocks.models.Inflation
import com.sugarspoon.pocketstocks.models.MarketStatus
import com.sugarspoon.pocketstocks.models.Stock
import com.sugarspoon.pocketstocks.models.StockDetail

data class HomeUiState(
    val allStocks: List<Stock> = listOf(),
    val filterStocks: List<Stock> = listOf(),
    val topStocks: List<Stock> = listOf(),
    val topFIIStocks: List<Stock> = listOf(),
    val marketCap: List<Stock> = listOf(),
    val stockDetail: StockDetail = StockDetail(),
    val error: String = "",
    val openModal: Boolean = false,
    val openHistoricalModal: Boolean = false,
    val marketStatusLoading: Boolean = false,
    val inflationLoading: Boolean = false,
    val detailsLoading: Boolean = false,
    val displaySkeleton: Boolean = false,
    val isRefreshing: Boolean = false,
    val code: String = "",
    val logo: String = "",
    val query: String = "",
    val marketB3: StockDetail = StockDetail(),
    val marketStatus: MarketStatus = MarketStatus("", false),
    val inflation: Inflation = Inflation(),
) : UiStates