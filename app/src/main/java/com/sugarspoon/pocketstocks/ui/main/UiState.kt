package com.sugarspoon.pocketstocks.ui.main

import com.sugarspoon.design_system.chart.models.DataPoint
import com.sugarspoon.pocketstocks.base.UiStates
import com.sugarspoon.pocketstocks.models.Inflation
import com.sugarspoon.pocketstocks.models.MarketStatus
import com.sugarspoon.pocketstocks.models.SegmentOptions
import com.sugarspoon.pocketstocks.models.Stock
import com.sugarspoon.pocketstocks.models.StockDetail
import com.sugarspoon.pocketstocks.models.SummaryStock

data class UiState(
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
    val isChartLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val code: String = "",
    val logo: String = "",
    val query: String = "",
    val marketB3: StockDetail = StockDetail(),
    val marketStatus: MarketStatus = MarketStatus("", false),
    val displaySaveButton: Boolean = false,
    val stockPreferences: List<SummaryStock> = listOf(),
    val walletEmptyState: Boolean = true,
    val inflation: Inflation = Inflation(),
    val historicalDataPrice: List<DataPoint> = listOf(),
    val selectedSegment: SegmentOptions = SegmentOptions.ONE_DAY,
    val balance: Float = 0f
) : UiStates