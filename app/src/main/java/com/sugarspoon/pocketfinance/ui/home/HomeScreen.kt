package com.sugarspoon.pocketfinance.ui.home

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sugarspoon.design_system.Dimensions
import com.sugarspoon.design_system.Dimensions.bottomSpacing
import com.sugarspoon.design_system.Dimensions.inlineSpacingMedium
import com.sugarspoon.design_system.Dimensions.topSpacing
import com.sugarspoon.design_system.Dimensions.topSpacingSmall
import com.sugarspoon.design_system.Dimensions.topSpacingVerySmall
import com.sugarspoon.design_system.chart.LineChart
import com.sugarspoon.design_system.chart.LinearChartEntry
import com.sugarspoon.design_system.chart.models.DataPoint
import com.sugarspoon.design_system.lists.SugarLists
import com.sugarspoon.design_system.lists.SugarLists.InflationItem
import com.sugarspoon.design_system.lists.skeleton
import com.sugarspoon.design_system.overlay.Modal
import com.sugarspoon.design_system.overlay.rememberOverlayState
import com.sugarspoon.design_system.text.SugarText
import com.sugarspoon.design_system.text.SugarSearchField
import com.sugarspoon.pocketfinance.utils.getDateTime
import java.util.Locale

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    uiState: UiState
) {
    val context = LocalContext.current
    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.isRefreshing,
        onRefresh = viewModel::onRefresh
    )
    val focusManager = LocalFocusManager.current
    if(uiState.query.isEmpty()) {
        focusManager.clearFocus()
    }

    LaunchedEffect(uiState.code) {
        if (uiState.code.isNotEmpty() && uiState.logo.isNotEmpty()) {
            val intent =
                Intent(context, com.sugarspoon.pocketfinance.ui.details.DetailsActivity::class.java)
            intent.putExtra("code", uiState.code)
            intent.putExtra("logo", uiState.logo)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(intent)
            viewModel.clearParams()
        }
    }
    val modalState = rememberOverlayState()
    val lazyListState = rememberLazyListState()

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        Column(modifier = Modifier.fillMaxSize()) {
            SugarSearchField(
                text = uiState.query,
                label = "Buscar Ativos na B3",
                onClose = viewModel::clearQuery,
                onValueChange = viewModel::onQueryChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .inlineSpacingMedium()
                    .topSpacing()
                    .bottomSpacing()
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                userScrollEnabled = true,
                state = lazyListState
            ) {
                if (uiState.displaySkeleton) {
                    items(
                        count = 10,
                        itemContent = { index ->
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .inlineSpacingMedium()
                                    .topSpacingSmall()
                                    .skeleton(true, shape = Dimensions.ListItemShape)
                            )
                        },
                    )
                } else {
                    items(
                        count = uiState.filterStocks.size,
                        key = {
                            uiState.filterStocks[it].stock ?: 0
                        },
                        itemContent = { index ->
                            val stock = uiState.filterStocks[index]
                            val bottomModifier =
                                if (index == uiState.filterStocks.lastIndex) {
                                    Modifier.padding(bottom = 100.dp)
                                } else {
                                    Modifier
                                }

                            if(index == 0) {
                                SugarLists.MarketB3Item(
                                    date = uiState.marketB3.regularMarketTime.getDateTime(),
                                    points = String.format(
                                        locale = Locale.getDefault(),
                                        "%.2f",
                                        uiState.marketB3.regularMarketPrice.toDouble()
                                    ),
                                    change = String.format(
                                        locale = Locale.getDefault(),
                                        "%.2f",
                                        uiState.marketB3.regularMarketChange.toDouble()
                                    )
                                            + " (${
                                        String.format(
                                            locale = Locale.getDefault(),
                                            "%.2f",
                                            uiState.marketB3.regularMarketChangePercent.toDouble()
                                        )
                                    } %)",
                                    regularMarketChange = uiState.marketB3.regularMarketChangePercent,
                                    modifier = Modifier
                                        .inlineSpacingMedium()
                                        .bottomSpacing()
                                        .topSpacingVerySmall(),
                                    isLoading = uiState.displaySkeleton,
                                    status = uiState.marketStatus.message,
                                    isOpened = uiState.marketStatus.isOpened
                                )
                                Divider()
                                val listOfDataPoint = mutableListOf<DataPoint>()
                                uiState.historicalInflation.forEach {
                                    listOfDataPoint.add(
                                        DataPoint(
                                            y = it.value.toDouble(),
                                            xLabel = it.date,
                                            yLabel = it.value.toString()
                                        )
                                    )
                                }
                                InflationItem(
                                    modifier = Modifier
                                        .inlineSpacingMedium()
                                        .bottomSpacing()
                                        .topSpacingVerySmall(),
                                    isLoading = uiState.displaySkeleton,
                                    value = uiState.inflation.value,
                                    date = uiState.inflation.date,
                                    onClick = viewModel::openInflationHistorical,
                                    data = listOfDataPoint
                                )
                                Divider()
                                SugarText(
                                    modifier = Modifier
                                        .inlineSpacingMedium()
                                        .bottomSpacing()
                                        .topSpacing(),
                                    text = "ATIVOS NA B3",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                )
                            }
                            SugarLists.StockItem(
                                logo = stock.logo.orEmpty(),
                                name = stock.name.orEmpty(),
                                code = stock.stock.orEmpty(),
                                sector = stock.sector.orEmpty(),
                                modifier = Modifier
                                    .then(bottomModifier)
                                    .inlineSpacingMedium()
                                    .topSpacingVerySmall(),
                                isLoading = false,
                                onClick = viewModel::openDetails
                            )
                        },
                    )
                }
            }
        }
        PullRefreshIndicator(
            refreshing = uiState.isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(
                Alignment.TopCenter
            ),
            backgroundColor = MaterialTheme.colorScheme.primary,
        )
    }

    modalState.handleVisibility(uiState.openModal)

    Modal(state = modalState, onDismissListener = {}) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
        ) {
            SugarText(
                text = uiState.error ?: "Falha na request",
                modifier = Modifier
                    .fillMaxWidth()
                    .inlineSpacingMedium(),
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.titleMedium
            )
            SugarText(
                text = uiState.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .inlineSpacingMedium(),
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
    HistoricalModal(
        display = false,
        data = uiState.historicalInflation.map {
            LinearChartEntry(
                value = it.value.toFloat(),
                date = it.date
            )
        }
    )
}

@Composable
fun HistoricalModal(display: Boolean, data: List<LinearChartEntry>) {
    val overlayState = rememberOverlayState()
    if(display) overlayState.display() else overlayState.hide()

    Modal(state = overlayState, onDismissListener = {}) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
        ) {
            SugarText(
                text = "Histórico de Inflação 2022/2023",
                modifier = Modifier
                    .topSpacing()
                    .fillMaxWidth()
                    .inlineSpacingMedium(),
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            val listOfDataPoint = mutableListOf<DataPoint>()
            data.forEach {
                listOfDataPoint.add(
                    DataPoint(
                        y = it.value.toDouble(),
                        xLabel = it.date,
                        yLabel = it.value.toString()
                    )
                )
            }
            LineChart(
                data = listOfDataPoint,
                graphColor = MaterialTheme.colorScheme.primary,
                showDashedLine = false,
                modifier = Modifier.fillMaxWidth().height(100.dp).topSpacing()
            )
        }
    }


}