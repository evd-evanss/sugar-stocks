package com.sugarspoon.pocketstocks.ui.details

import com.sugarspoon.design_system.chart.models.DataPoint
import com.sugarspoon.pocketstocks.base.UiStates
import com.sugarspoon.pocketstocks.models.Inflation
import com.sugarspoon.pocketstocks.models.MarketStatus
import com.sugarspoon.pocketstocks.models.SegmentOptions
import com.sugarspoon.pocketstocks.models.Stock
import com.sugarspoon.pocketstocks.models.StockDetail
import com.sugarspoon.pocketstocks.models.SummaryStock

data class DetailsUiState(
    val stockDetail: StockDetail = StockDetail(),
    val error: String = "",
    val openModal: Boolean = false,
    val detailsLoading: Boolean = false,
    val isChartLoading: Boolean = false,
    val code: String = "",
    val logo: String = "",
    val displaySaveButton: Boolean = false,
    val historicalDataPrice: List<DataPoint> = listOf(),
    val selectedSegment: SegmentOptions = SegmentOptions.ONE_DAY
) : UiStates