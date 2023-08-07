package com.sugarspoon.pocketfinance.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewModelScope
import com.sugarspoon.data.domain.entity.StockEntity
import com.sugarspoon.data.repositories.ApiRepository
import com.sugarspoon.data.repositories.LocalRepository
import com.sugarspoon.data.response.Inflation
import com.sugarspoon.data.response.MarketStatus
import com.sugarspoon.data.response.Stock
import com.sugarspoon.design_system.chart.models.DataPoint
import com.sugarspoon.pocketfinance.base.BaseViewModelMVI
import com.sugarspoon.pocketfinance.base.ScreeenState
import com.sugarspoon.pocketfinance.base.ScreenEvents
import com.sugarspoon.pocketfinance.models.StockDetail
import com.sugarspoon.pocketfinance.ui.mappers.UiMapper
import com.sugarspoon.pocketfinance.utils.formatWith2DecimalPlaces
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ApiRepository,
    private val localRepository: LocalRepository,
    private val uiMapper: UiMapper
) : BaseViewModelMVI<UiState, UiEvents>(UiState()) {

    override val uiState: @Composable () -> UiState
        get() = {
            val homeScreenState by state.collectAsState()
            homeScreenState
        }

    override fun handleEvents(oldState: UiState, event: UiEvents) {
        when (event) {
            is UiEvents.LoadStocks -> {
                createNewState(newState = oldState.copy(displaySkeleton = true))
                fetchQuoteList(oldState)
            }

            is UiEvents.LoadStocksPreferences -> {
                fetchWalletStocks(oldState)
            }

            is UiEvents.InflationHistorical -> {
                fetchInflationHistorical(oldState)
            }

            is UiEvents.LoadMarketStatus -> {
                fetchMarketStatus(oldState)
            }

            is UiEvents.SeeQuotDetail -> {
                createNewState(newState = oldState.copy(detailsLoading = true))
                fetchDetail(oldState, event.ticker)
            }

            is UiEvents.SelectSegment -> {
                val segmentSelected =
                    SegmentOptions.values().first { it.option == event.segmentOption }
                val request = SegmentedRequest(
                    ticker = oldState.code,
                    range = segmentSelected.option
                )
                if (segmentSelected != oldState.selectedSegment) {
                    createNewState(newState = oldState.copy(isChartLoading = true))
                    fetchDetailWithRange(oldState, request, segmentSelected)
                }
            }

            is UiEvents.ClearParams -> {
                createNewState(
                    newState = oldState.copy(
                        code = "",
                        logo = ""
                    )
                )
            }

            is UiEvents.ClearQuery -> {
                createNewState(
                    newState = oldState.copy(
                        query = "",
                        filterStocks = oldState.allStocks
                    )
                )
            }

            is UiEvents.OpenDetails -> {
                createNewState(
                    newState = oldState.copy(
                        code = event.code,
                        logo = event.logo
                    )
                )
            }

            is UiEvents.FindStockInWallet -> {
                fetchWalletStock(oldState, event.code)
            }

            is UiEvents.OrderListBy -> {
                createNewState(
                    newState = oldState.copy(
                        query = event.query,
                        filterStocks = if (event.query.isEmpty()) {
                            oldState.allStocks
                        } else {
                            oldState.allStocks.filter {
                                it.sector.orEmpty().startsWith(event.query)
                                        || it.sector.orEmpty().startsWith(event.query)
                                        || it.sector.orEmpty()
                                    .startsWith(event.query.compatCapitalize())
                                        || it.sector.orEmpty().startsWith(event.query.uppercase())
                                        || it.sector.orEmpty().startsWith(event.query.lowercase())
                                        || it.sector.orEmpty().contains(event.query)
                                        || it.stock.orEmpty().startsWith(event.query)
                                        || it.stock.orEmpty()
                                    .startsWith(event.query.compatCapitalize())
                                        || it.stock.orEmpty().startsWith(event.query.uppercase())
                                        || it.stock.orEmpty().startsWith(event.query.lowercase())
                                        || it.stock.orEmpty().contains(event.query)
                            }
                        }
                    )
                )
            }

            is UiEvents.SaveStockDetail -> {
                viewModelScope.launch {
                    val response = localRepository.save(
                        StockEntity(
                            code = event.code,
                            name = oldState.stockDetail.longName,
                            sector = "",
                            logo = event.logo
                        )
                    )
                }
            }

            is UiEvents.RemoveStockDetailItem -> {
                viewModelScope.launch {
                    val response = localRepository.delete(event.code)
                    emitEvent(UiEvents.LoadStocksPreferences)
                }
            }
        }
    }

    private fun fetchMarketStatus(oldState: UiState) = viewModelScope.launch {
        repository.getMarketStatus()
            .zip(repository.getInflation("brazil")) { marketStatus, inflationDTO ->
                createNewState(
                    newState = oldState.copy(
                        marketStatus = marketStatus,
                        inflation = inflationDTO.inflation.first(),
                        detailsLoading = false
                    )
                )
            }
            .collect()
    }

    private fun fetchInflationHistorical(oldState: UiState) = viewModelScope.launch {
        repository.getInflation(
            country = "brazil",
            start = "01/06/2022",
            end = "05/08/2023",
            historical = true
        ).collect { inflationDTO ->
            createNewState(
                newState = oldState.copy(
                    historicalInflation = inflationDTO.inflation,
                    openHistoricalModal = true,
                    detailsLoading = false
                )
            )
        }
    }

    private fun fetchQuoteList(oldState: UiState) = viewModelScope.launch {
        repository.getQuoteList()
            .zip(repository.getDetail("^BVSP")) { brapiResponse, detailsResponse ->
                createNewState(
                    newState = oldState.copy(
                        allStocks = brapiResponse.stocks,
                        filterStocks = brapiResponse.stocks,
                        topStocks = brapiResponse.stocks.sortedByDescending { it.change?.toDouble() }
                            .take(5),
                        marketCap = brapiResponse.stocks.sortedByDescending { it.marketCap?.toDouble() }
                            .take(5),
                        topFIIStocks = brapiResponse.stocks.filter {
                            it.name.orEmpty().contains("FII ")
                        }.sortedByDescending { it.change?.toDouble() }.take(5),
                        displaySkeleton = false,
                        marketB3 = uiMapper.mapToStockDetail(detailsResponse.results.first())
                    )
                )
                emitEvent(UiEvents.LoadMarketStatus)
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
            .collect {
                createNewState(
                    oldState.copy(
                        stockPreferences = it,
                        walletEmptyState = it.isEmpty()
                    )
                )
            }
    }

    fun onRefresh() {
        emitEvent(
            UiEvents.LoadStocks,
            UiEvents.LoadMarketStatus
        )
    }

    fun getQuoteList() = run {
        emitEvent(UiEvents.LoadStocks)
    }

    fun getDetail(ticker: String) {
        emitEvent(
            UiEvents.SeeQuotDetail(ticker)
        )
    }

    fun setSegmentOption(segment: SegmentOptions) {
        emitEvent(UiEvents.SelectSegment(segment.option))
    }

    fun findStockInWallet(ticker: String) {
        emitEvent(
            UiEvents.FindStockInWallet(ticker)
        )
    }

    fun onQueryChange(query: String) {
        emitEvent(UiEvents.OrderListBy(query))
    }

    fun openDetails(code: String, logo: String) {
        emitEvent(
            UiEvents.OpenDetails(code, logo)
        )
    }

    fun clearParams() {
        emitEvent(UiEvents.ClearParams)
    }

    fun clearQuery() {
        emitEvent(UiEvents.ClearQuery)
    }

    fun saveStockDetail(code: String, logo: String) {
        emitEvent(UiEvents.SaveStockDetail(code, logo))
    }

    fun loadStockPreferences() {
        emitEvent(UiEvents.LoadStocksPreferences)
    }

    fun onClickUnfollow(code: String) {
        emitEvent(UiEvents.RemoveStockDetailItem(code))
    }

    fun openInflationHistorical() {
        emitEvent(UiEvents.InflationHistorical)
    }

    private fun String.compatCapitalize(): String {
        return replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
    }
}

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
    val detailsLoading: Boolean = false,
    val displaySkeleton: Boolean = false,
    val isChartLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val code: String = "",
    val logo: String = "",
    val query: String = "",
    val marketB3: StockDetail = StockDetail(),
    val marketStatus: MarketStatus = MarketStatus("AA", false),
    val displaySaveButton: Boolean = false,
    val stockPreferences: List<StockEntity> = listOf(),
    val walletEmptyState: Boolean = true,
    val inflation: Inflation = Inflation(),
    val historicalInflation: List<Inflation> = listOf(),
    val historicalDataPrice: List<DataPoint> = listOf(),
    val selectedSegment: SegmentOptions = SegmentOptions.ONE_DAY
) : ScreeenState

sealed class UiEvents : ScreenEvents {
    object LoadStocks : UiEvents()
    object LoadMarketStatus : UiEvents()
    object ClearParams : UiEvents()
    object ClearQuery : UiEvents()
    object LoadStocksPreferences : UiEvents()
    object InflationHistorical : UiEvents()
    data class SeeQuotDetail(val ticker: String) : UiEvents()
    data class SelectSegment(val segmentOption: String) : UiEvents()
    data class OpenDetails(val code: String, val logo: String) : UiEvents()
    data class SaveStockDetail(val code: String, val logo: String) : UiEvents()
    data class RemoveStockDetailItem(val code: String) : UiEvents()
    data class FindStockInWallet(val code: String) : UiEvents()
    data class OrderListBy(val query: String) : UiEvents()
}

data class SegmentedRequest(
    val ticker: String,
    val range: String,
    val interval: String = "60m"
)

enum class SegmentOptions(val option: String, val text: String) {
    ONE_DAY(option = "1d", text = "1D"),
    FIVE_DAY(option = "5d", text = "5D"),
    ONE_MONTH(option = "1mo", text = "1M"),
    THREE_MONTH(option = "3mo", text = "3M"),
    SIX_MONTH(option = "6mo", text = "6M"), ;
}