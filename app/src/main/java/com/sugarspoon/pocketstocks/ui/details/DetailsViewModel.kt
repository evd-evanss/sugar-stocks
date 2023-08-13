package com.sugarspoon.pocketstocks.ui.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewModelScope
import com.sugarspoon.design_system.chart.models.DataPoint
import com.sugarspoon.domain.usecase.local.FindPreferenceUseCase
import com.sugarspoon.domain.usecase.local.SavePreferenceUseCase
import com.sugarspoon.domain.usecase.remote.FetchDetailUseCase
import com.sugarspoon.pocketstocks.base.BaseViewModel
import com.sugarspoon.pocketstocks.models.SegmentOptions
import com.sugarspoon.pocketstocks.models.SegmentedRequest
import com.sugarspoon.pocketstocks.models.SummaryStock
import com.sugarspoon.pocketstocks.ui.mappers.UiMapper
import com.sugarspoon.pocketstocks.utils.formatWith2DecimalPlaces
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val fetchDetailUseCase: FetchDetailUseCase,
    private val savePreferenceUseCase: SavePreferenceUseCase,
    private val findPreferenceUseCase: FindPreferenceUseCase,
    private val uiMapper: UiMapper
) : BaseViewModel<DetailsUiState>(DetailsUiState()) {

    override val uiState: @Composable () -> DetailsUiState
        get() = {
            val uiStateFlow by state.collectAsState()
            uiStateFlow
        }

    fun getDetail(ticker: String) {
        createNewState(newState = currentState.value.copy(detailsLoading = true))
        fetchDetail(currentState.value, ticker)
    }

    fun setSegmentOption(segment: SegmentOptions) {
        val segmentSelected =
            SegmentOptions.values().first { it.option == segment.option }
        val request = SegmentedRequest(
            ticker = currentState.value.code,
            range = segmentSelected.option,
            interval = segmentSelected.interval
        )
        if (segmentSelected != currentState.value.selectedSegment) {
            createNewState(newState = currentState.value.copy(isChartLoading = true))
            fetchDetailWithRange(currentState.value, request, segmentSelected)
        }
    }

    fun findStockInWallet(ticker: String) = fetchWalletStock(ticker)

    fun saveStockDetail(code: String, logo: String) = viewModelScope.launch {
        savePreferenceUseCase.invoke(
            uiMapper.mapSummaryStockEntity(
                SummaryStock(
                    code = code,
                    name = currentState.value.stockDetail.longName,
                    sector = "",
                    logo = logo
                )
            )
        )
        clearUiState()
    }

    fun clearUiState() {
        currentState.value = DetailsUiState()
    }

    private fun fetchDetail(oldState: DetailsUiState, ticker: String) = viewModelScope.launch {
        fetchDetailUseCase.invoke(ticker, range = SegmentOptions.ONE_DAY.option, interval = "60m")
            .onSuccess { response ->
                val result =
                    uiMapper.mapToHistoricalDataPrice(response.results.first().historicalDataPrice)
                val dataPoint = mutableListOf<DataPoint>()
                result.forEach { historicalDataPrice ->
                    historicalDataPrice?.let {
                        dataPoint.add(
                            DataPoint(
                                y = historicalDataPrice.openPrice.toDouble(),
                                yLabel = historicalDataPrice.openPrice.toDouble()
                                    .formatWith2DecimalPlaces(),
                                xLabel = historicalDataPrice.date.toString()
                            )
                        )
                    }

                }
                createNewState(
                    newState = oldState.copy(
                        stockDetail = uiMapper.mapToStockDetail(response.results.first()),
                        detailsLoading = false,
                        historicalDataPrice = dataPoint,
                        code = ticker
                    )
                )
            }
            .onFailure {
                createNewState(
                    newState = oldState.copy(
                        error = "$it",
                        detailsLoading = false,
                        openModal = true,
                        code = ticker
                    )
                )
            }
    }

    private fun fetchDetailWithRange(
        oldState: DetailsUiState,
        segmentedDetail: SegmentedRequest,
        selectedSegment: SegmentOptions,
    ) = viewModelScope.launch {
        fetchDetailUseCase.invoke(
            tickers = segmentedDetail.ticker,
            range = segmentedDetail.range,
            interval = segmentedDetail.interval
        )
            .onSuccess { response ->
                val dataPoint = mutableListOf<DataPoint>()
                val chartData =
                    uiMapper.mapToHistoricalDataPrice(response.results.first().historicalDataPrice)
                chartData.forEach { historicalDataPrice ->
                    historicalDataPrice?.let {dataPoint.add(
                        DataPoint(
                            y = historicalDataPrice.openPrice.toDouble(),
                            yLabel = historicalDataPrice.openPrice.toDouble()
                                .formatWith2DecimalPlaces(),
                            xLabel = historicalDataPrice.date.toString()
                        )
                    )
                    }

                }
                createNewState(
                    newState = oldState.copy(
                        historicalDataPrice = dataPoint,
                        isChartLoading = false,
                        selectedSegment = selectedSegment
                    )
                )
            }
            .onFailure {
                withContext(Main) {
                    createNewState(
                        newState = oldState.copy(
                            error = "$it",
                            isChartLoading = false,
                            openModal = true,
                            selectedSegment = selectedSegment
                        )
                    )
                }
            }
    }

    private fun fetchWalletStock(code: String) = viewModelScope.launch {
        findPreferenceUseCase.invoke(code)
            .collect {
                createNewState(currentState.value.copy(displaySaveButton = it == null))
            }
    }
}

