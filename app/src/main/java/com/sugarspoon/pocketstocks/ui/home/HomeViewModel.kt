package com.sugarspoon.pocketstocks.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewModelScope
import com.sugarspoon.domain.usecase.remote.FetchQuoteListUseCase
import com.sugarspoon.pocketstocks.base.BaseViewModelMVI
import com.sugarspoon.pocketstocks.ui.mappers.UiMapper
import java.net.UnknownHostException
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeViewModel @Inject constructor(
    private val fetchQuoteListUseCase: FetchQuoteListUseCase,
    private val uiMapper: UiMapper
) : BaseViewModelMVI<HomeUiState>(HomeUiState()) {

    override val uiState: @Composable () -> HomeUiState
        get() = {
            val uiStateFlow by state.collectAsState()
            uiStateFlow
        }

    fun onRefresh() {
        createNewState(newState = currentState.value.copy(displaySkeleton = true))
        fetchApiData()
    }

    fun getAnalyzeData() = viewModelScope.launch {
        val stocks = currentState.value.allStocks
        val topStocks = async {
            stocks.sortedByDescending { it.change.toDouble() }
                .filter {
                    it.name.contains("FII ").not()
                }
                .take(5)
        }.await()
        createNewState(
            newState = currentState.value.copy(
//                topFIIStocks = stocks.filter {
//                    it.name.contains("FII ")
//                }.sortedByDescending { it.change.toDouble() }.take(5),
                topStocks = topStocks,
//                marketCap = stocks.sortedByDescending { it.marketCap.toDouble() }
//                    .take(5)
            )
        )
    }

    fun onQueryChange(query: String) {
        createNewState(
            newState = currentState.value.copy(
                query = query,
                filterStocks = if (query.isEmpty()) {
                    currentState.value.allStocks
                } else {
                    currentState.value.allStocks.filter {
                        it.sector.startsWith(query)
                                || it.sector.startsWith(query)
                                || it.sector
                            .startsWith(query.compatCapitalize())
                                || it.sector.startsWith(query.uppercase())
                                || it.sector.startsWith(query.lowercase())
                                || it.sector.contains(query)
                                || it.stock.startsWith(query)
                                || it.stock
                            .startsWith(query.compatCapitalize())
                                || it.stock.startsWith(query.uppercase())
                                || it.stock.startsWith(query.lowercase())
                                || it.stock.contains(query)
                    }
                }
            )
        )
    }

    fun openDetails(code: String, logo: String) {
        createNewState(
            newState = currentState.value.copy(
                code = code,
                logo = logo
            )
        )
    }

    fun clearParams() {
        createNewState(
            newState = currentState.value.copy(
                code = "",
                logo = ""
            )
        )
    }

    fun clearQuery() {
        createNewState(
            newState = currentState.value.copy(
                query = "",
                filterStocks = currentState.value.allStocks
            )
        )
    }

    private fun fetchApiData() = viewModelScope.launch {
        fetchQuoteListUseCase.invoke()
            .onSuccess { response ->
                val stocks = uiMapper.mapStockList(response.responseDto)
                createNewState(
                    newState = currentState.value.copy(
                        allStocks = stocks,
                        filterStocks = stocks,
                        displaySkeleton = false,
                        inflation = uiMapper.mapInflation(response.inflationResponseDTO.inflation.first()),
                        marketStatus = uiMapper.mapMarketStatus(response.marketStatusDto),
                        marketB3 = uiMapper.mapToStockDetail(response.quotesResponseDto.results.first()),
                        openModal = false,
                    )
                )
            }
            .onFailure {
                val message = when(it) {
                    is UnknownHostException -> "Verifique sua internet e tente novamente."
                    else -> "Estamos passando por instabilidades no momento, tente voltar aqui mais tarde."
                }
                createNewState(
                    newState = currentState.value.copy(
                        error = message,
                        displaySkeleton = false,
                        allStocks = listOf(),
                        openModal = true
                    )
                )
            }
    }

    private fun String.compatCapitalize(): String {
        return replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
    }
}

