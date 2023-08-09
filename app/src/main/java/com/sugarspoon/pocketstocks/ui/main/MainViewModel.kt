package com.sugarspoon.pocketstocks.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewModelScope
import com.sugarspoon.design_system.chart.models.DataPoint
import com.sugarspoon.domain.repositories.ApiRepository
import com.sugarspoon.domain.repositories.LocalRepository
import com.sugarspoon.pocketstocks.base.BaseViewModelMVI
import com.sugarspoon.pocketstocks.models.SegmentOptions
import com.sugarspoon.pocketstocks.models.SegmentedRequest
import com.sugarspoon.pocketstocks.models.SummaryStock
import com.sugarspoon.pocketstocks.ui.mappers.UiMapper
import com.sugarspoon.pocketstocks.utils.formatWith2DecimalPlaces
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ApiRepository,
    private val localRepository: LocalRepository,
    private val uiMapper: UiMapper
) : BaseViewModelMVI<UiState, UiEvent>(UiState()) {

    override val uiState: @Composable () -> UiState
        get() = {
            val uiStateFlow by state.collectAsState()
            uiStateFlow
        }

    override fun handleEvents(oldState: UiState, event: UiEvent) {
        when (event) {
            is UiEvent.LoadStocks -> {
                createNewState(newState = oldState.copy(displaySkeleton = true))
                fetchQuoteList(oldState)
            }

            is UiEvent.LoadStocksPreferences -> {
                fetchWalletStocks(oldState)
            }

            is UiEvent.LoadMarketStatus -> {
                fetchMarketStatus(oldState)
            }

            is UiEvent.LoadDataInflation -> {
                fetchDataOfInflation(oldState)
            }

            is UiEvent.SeeQuotDetail -> {
                createNewState(newState = oldState.copy(detailsLoading = true))
                fetchDetail(oldState, event.ticker)
            }

            is UiEvent.SelectSegment -> {
                val segmentSelected =
                    SegmentOptions.values().first { it.option == event.segmentOption }
                val request = SegmentedRequest(
                    ticker = oldState.code,
                    range = segmentSelected.option,
                    interval = segmentSelected.interval
                )
                if (segmentSelected != oldState.selectedSegment) {
                    createNewState(newState = oldState.copy(isChartLoading = true))
                    fetchDetailWithRange(oldState, request, segmentSelected)
                }
            }

            is UiEvent.ClearParams -> {
                createNewState(
                    newState = oldState.copy(
                        code = "",
                        logo = ""
                    )
                )
            }

            is UiEvent.ClearQuery -> {
                createNewState(
                    newState = oldState.copy(
                        query = "",
                        filterStocks = oldState.allStocks
                    )
                )
            }

            is UiEvent.OpenDetails -> {
                createNewState(
                    newState = oldState.copy(
                        code = event.code,
                        logo = event.logo
                    )
                )
            }

            is UiEvent.FindStockInWallet -> {
                fetchWalletStock(oldState, event.code)
            }

            is UiEvent.OrderListBy -> {
                createNewState(
                    newState = oldState.copy(
                        query = event.query,
                        filterStocks = if (event.query.isEmpty()) {
                            oldState.allStocks
                        } else {
                            oldState.allStocks.filter {
                                it.sector.startsWith(event.query)
                                        || it.sector.startsWith(event.query)
                                        || it.sector
                                    .startsWith(event.query.compatCapitalize())
                                        || it.sector.startsWith(event.query.uppercase())
                                        || it.sector.startsWith(event.query.lowercase())
                                        || it.sector.contains(event.query)
                                        || it.stock.startsWith(event.query)
                                        || it.stock
                                    .startsWith(event.query.compatCapitalize())
                                        || it.stock.startsWith(event.query.uppercase())
                                        || it.stock.startsWith(event.query.lowercase())
                                        || it.stock.contains(event.query)
                            }
                        }
                    )
                )
            }

            is UiEvent.SaveStockDetail -> {
                viewModelScope.launch {
                    localRepository.save(
                        uiMapper.mapSummaryStockEntity(
                            SummaryStock(
                                code = event.code,
                                name = oldState.stockDetail.longName,
                                sector = "",
                                logo = event.logo
                            )
                        )
                    )
                }
            }

            is UiEvent.RemoveStockDetailItem -> {
                viewModelScope.launch {
                    val response = localRepository.delete(event.code)
                    emitEvent(UiEvent.LoadStocksPreferences)
                }
            }
        }
    }

    private fun fetchMarketStatus(oldState: UiState) = viewModelScope.launch {
        repository.getMarketStatus()
            .zip(repository.getDetail("^BVSP")) { marketStatusDto, detailsResponse ->
                createNewState(
                    newState = oldState.copy(
                        marketStatus = uiMapper.mapMarketStatus(marketStatusDto),
                        displaySkeleton = false,
                        marketB3 = uiMapper.mapToStockDetail(detailsResponse.results.first())
                    )
                )
                emitEvent(UiEvent.LoadDataInflation)
            }
            .catch {
                createNewState(
                    newState = oldState.copy(
                        error = "Erro: $it",
                        openModal = true,
                        marketStatus = oldState.marketStatus,
                        marketB3 = oldState.marketB3
                    )
                )
            }.collect()
    }

    private fun fetchDataOfInflation(oldState: UiState) = viewModelScope.launch {
        repository.getInflation("brazil")
            .onStart {
                createNewState(
                    oldState.copy(inflationLoading = true)
                )
            }
            .catch {
                withContext(Main) {
                    createNewState(
                        newState = oldState.copy(
                            error = "Erro: $it",
                            openModal = true,
                            inflation = oldState.inflation,
                            inflationLoading = false
                        )
                    )
                }
            }
            .collect { inflationDTO ->
                createNewState(
                    newState = oldState.copy(
                        inflation = uiMapper.mapInflation(inflationDTO.inflation.first()),
                        inflationLoading = false
                    )
                )
            }
    }

    private fun fetchQuoteList(oldState: UiState) = viewModelScope.launch {
        repository.getQuoteList()
            .zip(repository.getDetail("^BVSP")) { brapiResponse, detailsResponse ->
                val stocks = uiMapper.mapStockList(brapiResponse)
                createNewState(
                    newState = oldState.copy(
                        allStocks = stocks,
                        filterStocks = stocks,
                        topStocks = stocks.sortedByDescending { it.change.toDouble() }
                            .take(5),
                        marketCap = stocks.sortedByDescending { it.marketCap.toDouble() }
                            .take(5),
                        topFIIStocks = stocks.filter {
                            it.name.orEmpty().contains("FII ")
                        }.sortedByDescending { it.change.toDouble() }.take(5),
                        displaySkeleton = false,
                        marketB3 = uiMapper.mapToStockDetail(detailsResponse.results.first())
                    )
                )
                emitEvent(UiEvent.LoadMarketStatus)
            }
            .catch {
                withContext(Main) {
                    createNewState(
                        newState = oldState.copy(
                            error = "Erro: $it",
                            displaySkeleton = false,
                            allStocks = listOf()
                        )
                    )
                }
            }.collect()
    }

    private fun fetchDetail(oldState: UiState, ticker: String) = viewModelScope.launch {
        repository.getDetail(ticker, range = SegmentOptions.ONE_DAY.option, interval = "60m")
            .catch {
                withContext(Main) {
                    createNewState(
                        newState = oldState.copy(
                            error = "$it",
                            detailsLoading = false,
                            allStocks = listOf(),
                            openModal = true,
                            code = ticker
                        )
                    )
                }
            }
            .collect {
                val result =
                    uiMapper.mapToHistoricalDataPrice(it.results.first().historicalDataPrice)
                val dataPoint = mutableListOf<DataPoint>()
                result.forEach { historicalDataPrice ->
                    dataPoint.add(
                        DataPoint(
                            y = historicalDataPrice.openPrice.toDouble(),
                            yLabel = historicalDataPrice.openPrice.toDouble().formatWith2DecimalPlaces(),
                            xLabel = historicalDataPrice.date.toString()
                        )
                    )
                }
                createNewState(
                    newState = oldState.copy(
                        stockDetail = uiMapper.mapToStockDetail(it.results.first()),
                        detailsLoading = false,
                        historicalDataPrice = dataPoint,
                        code = ticker
                    )
                )
            }
    }

    private fun fetchDetailWithRange(
        oldState: UiState,
        segmentedDetail: SegmentedRequest,
        selectedSegment: SegmentOptions,
    ) = viewModelScope.launch {
        repository.getDetail(
            tickers = segmentedDetail.ticker,
            range = segmentedDetail.range,
            interval = segmentedDetail.interval
        )
            .catch {
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
            .collect {
                val dataPoint = mutableListOf<DataPoint>()
                val chartData = uiMapper.mapToHistoricalDataPrice(it.results.first().historicalDataPrice)
                chartData.forEach { historicalDataPrice ->
                        dataPoint.add(
                            DataPoint(
                                y = historicalDataPrice.openPrice.toDouble(),
                                yLabel = historicalDataPrice.openPrice.toDouble().formatWith2DecimalPlaces(),
                                xLabel = historicalDataPrice.date.toString()
                            )
                        )
                    }
                createNewState(
                    newState = oldState.copy(
                        historicalDataPrice = dataPoint,
                        isChartLoading = false,
                        selectedSegment = selectedSegment
                    )
                )
            }
    }

    private fun fetchWalletStock(oldState: UiState, code: String) = viewModelScope.launch {
        localRepository.findNote(code)
            .collect {
                createNewState(oldState.copy(displaySaveButton = it == null))
            }
    }

    private fun fetchWalletStocks(oldState: UiState) = viewModelScope.launch {
        localRepository.getAllStocks()
            .catch {
                createNewState(
                    oldState.copy(
                        openModal = true,
                        error = it.message.orEmpty()
                    )
                )
            }
            .collect {
                createNewState(
                    oldState.copy(
                        stockPreferences = uiMapper.mapSummaryStockList(it),
                        walletEmptyState = it.isEmpty()
                    )
                )
            }
    }

    fun onRefresh() {
        emitEvent(
            UiEvent.LoadStocks,
            UiEvent.LoadMarketStatus
        )
    }

    fun getQuoteList() = run {
        emitEvent(UiEvent.LoadStocks)
    }

    fun getDetail(ticker: String) {
        emitEvent(
            UiEvent.SeeQuotDetail(ticker)
        )
    }

    fun setSegmentOption(segment: SegmentOptions) {
        emitEvent(UiEvent.SelectSegment(segment.option))
    }

    fun findStockInWallet(ticker: String) {
        emitEvent(
            UiEvent.FindStockInWallet(ticker)
        )
    }

    fun onQueryChange(query: String) {
        emitEvent(UiEvent.OrderListBy(query))
    }

    fun openDetails(code: String, logo: String) {
        emitEvent(
            UiEvent.OpenDetails(code, logo)
        )
    }

    fun clearParams() {
        emitEvent(UiEvent.ClearParams)
    }

    fun clearQuery() {
        emitEvent(UiEvent.ClearQuery)
    }

    fun saveStockDetail(code: String, logo: String) {
        emitEvent(UiEvent.SaveStockDetail(code, logo))
    }

    fun loadStockPreferences() {
        emitEvent(UiEvent.LoadStocksPreferences)
    }

    fun onClickUnfollow(code: String) {
        emitEvent(UiEvent.RemoveStockDetailItem(code))
    }

    private fun String.compatCapitalize(): String {
        return replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
    }
}

