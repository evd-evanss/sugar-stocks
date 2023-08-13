package com.sugarspoon.pocketstocks.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.sugarspoon.design_system.Dimensions.bottomSpacing
import com.sugarspoon.design_system.Dimensions.inlineSpacingMedium
import com.sugarspoon.design_system.Dimensions.topSpacing
import com.sugarspoon.design_system.Dimensions.topSpacingVerySmall
import com.sugarspoon.design_system.lists.SugarLists
import com.sugarspoon.design_system.lists.SugarLists.InflationItem
import com.sugarspoon.design_system.lists.sugarSkeleton
import com.sugarspoon.design_system.overlay.GenericDialog
import com.sugarspoon.design_system.overlay.rememberOverlayState
import com.sugarspoon.design_system.text.SugarSearchField
import com.sugarspoon.design_system.text.SugarText
import com.sugarspoon.pocketstocks.ui.details.DetailsActivity.Companion.getDetailsActivityIntent

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    uiState: HomeUiState,
    onFinish: () -> Unit
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.isRefreshing,
        onRefresh = viewModel::onRefresh
    )
    val errorState = rememberOverlayState()
    val lazyListState = rememberLazyListState()
    errorState.handleVisibility(uiState.openModal)

    LoadInitialData(viewModel = viewModel, uiState = uiState)

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            userScrollEnabled = true,
            state = lazyListState
        ) {
            item {
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
            }
            sugarSkeleton(
                isLoading = uiState.displaySkeleton,
                lazyListScope = this,
                count = 10,
                content = {
                    item {
                        SugarLists.MarketB3Item(
                            date = uiState.marketB3.regularMarketTime,
                            points = uiState.marketB3.regularMarketPrice,
                            change = uiState.marketB3.regularMarketChange + " (${uiState.marketB3.regularMarketChangePercent} %)",
                            regularMarketChange = uiState.marketB3.regularMarketChangePercent.replace(
                                ",",
                                "."
                            ).toDouble(),
                            modifier = Modifier
                                .inlineSpacingMedium()
                                .bottomSpacing()
                                .topSpacingVerySmall(),
                            status = uiState.marketStatus.message,
                            isOpened = uiState.marketStatus.isOpened
                        )
                        Divider()
                        InflationItem(
                            modifier = Modifier
                                .inlineSpacingMedium()
                                .topSpacing()
                                .bottomSpacing()
                                .topSpacingVerySmall(),
                            value = uiState.inflation.value,
                            date = uiState.inflation.date,
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
                            SugarLists.StockItem(
                                logo = stock.logo,
                                name = stock.name,
                                code = stock.stock,
                                sector = stock.sector,
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
            )
        }
        PullRefreshIndicator(
            refreshing = uiState.isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            backgroundColor = MaterialTheme.colorScheme.primary,
        )
    }
    GenericDialog(
        title = "Não foi possível recuperar novos dados",
        message = uiState.error,
        buttonTitlePositive = "Tentar novamente",
        buttonTitleNegative = "Fechar App",
        onActionPositive = viewModel::onRefresh,
        displayError = uiState.openModal,
        onActionNegative = onFinish
    )
}

@Composable
private fun LoadInitialData(
    viewModel: HomeViewModel,
    uiState: HomeUiState
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    if (uiState.query.isEmpty()) {
        focusManager.clearFocus()
    }
    LaunchedEffect(uiState.code) {
        if (uiState.code.isNotEmpty() && uiState.logo.isNotEmpty()) {
            context.startActivity(
                context.getDetailsActivityIntent(
                    code = uiState.code,
                    logo = uiState.logo
                )
            )
            viewModel.clearParams()
        }
    }
}