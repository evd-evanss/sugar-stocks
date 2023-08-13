package com.sugarspoon.pocketstocks.ui.analyze

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Badge
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.sugarspoon.design_system.Dimensions
import com.sugarspoon.design_system.Dimensions.bottomSpacing
import com.sugarspoon.design_system.Dimensions.bottomSpacingVerySmall
import com.sugarspoon.design_system.Dimensions.inlineSpacingMedium
import com.sugarspoon.design_system.Dimensions.inlineSpacingSmall
import com.sugarspoon.design_system.Dimensions.topSpacing
import com.sugarspoon.design_system.Dimensions.topSpacingVerySmall
import com.sugarspoon.design_system.lists.SugarLists
import com.sugarspoon.design_system.text.SugarText
import com.sugarspoon.design_system.topbar.TopBar
import com.sugarspoon.pocketstocks.ui.details.DetailsActivity.Companion.getDetailsActivityIntent
import com.sugarspoon.pocketstocks.ui.home.HomeUiState
import com.sugarspoon.pocketstocks.ui.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyzeScreen(
    viewModel: HomeViewModel,
    uiState: HomeUiState
) {
    val context = LocalContext.current
    LaunchedEffect(true) {
        viewModel.getAnalyzeData()
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
    Column {
        TopBar(
            title = "Análises - B3 e BDRs",
            modifier = Modifier
        )
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())) {
            SugarText(
                modifier = Modifier
                    .inlineSpacingMedium()
                    .bottomSpacing(),
                text = "TOP 5 - MAIORES ALTAS",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .bottomSpacing(),
                userScrollEnabled = true,
                contentPadding = PaddingValues(horizontal = Dimensions.SpacingSmall)
            ) {
                items(
                    count = uiState.topStocks.size,
                    key = {
                        uiState.topStocks[it].stock
                    },
                    itemContent = { index ->
                        val stock = uiState.topStocks[index]
                        Column {
                            Badge(
                                modifier = Modifier
                                    .inlineSpacingSmall()
                                    .bottomSpacingVerySmall(),
                                containerColor = MaterialTheme.colorScheme.primary,
                            ) {
                                SugarText(
                                    modifier = Modifier.inlineSpacingSmall(),
                                    text = "Top ${index+1}",
                                    style = MaterialTheme.typography.titleMedium,
                                )
                            }

                            SugarLists.TopStockItem(
                                logo = stock.logo,
                                name = stock.name,
                                code = stock.stock,
                                sector = stock.sector,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .inlineSpacingSmall()
                                    .topSpacingVerySmall(),
                                isLoading = false,
                                onClick = viewModel::openDetails
                            )
                        }
                    },
                )
            }
            Divider()
            SugarText(
                modifier = Modifier
                    .inlineSpacingMedium()
                    .bottomSpacing()
                    .topSpacing(),
                text = "TOP 5 - MAIORES ALTAS FII",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .bottomSpacing(),
                userScrollEnabled = true,
                contentPadding = PaddingValues(horizontal = Dimensions.SpacingSmall)
            ) {
                items(
                    count = uiState.topFIIStocks.size,
                    key = {
                        uiState.topFIIStocks[it].stock
                    },
                    itemContent = { index ->
                        val stock = uiState.topFIIStocks[index]
                        Column {
                            Badge(
                                modifier = Modifier
                                    .inlineSpacingSmall()
                                    .bottomSpacingVerySmall(),
                                containerColor = MaterialTheme.colorScheme.primary,
                            ) {
                                SugarText(
                                    modifier = Modifier.inlineSpacingSmall(),
                                    text = "Top ${index+1}",
                                    style = MaterialTheme.typography.titleMedium,
                                )
                            }

                            SugarLists.TopStockItem(
                                logo = stock.logo,
                                name = stock.name,
                                code = stock.stock,
                                sector = stock.sector,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .inlineSpacingSmall()
                                    .topSpacingVerySmall(),
                                isLoading = false,
                                onClick = viewModel::openDetails
                            )
                        }
                    },
                )
            }
            Divider()
            SugarText(
                modifier = Modifier
                    .inlineSpacingMedium()
                    .bottomSpacing()
                    .topSpacing(),
                text = "TOP 5 - MAIORES CAPITAIS",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                userScrollEnabled = true,
                contentPadding = PaddingValues(horizontal = Dimensions.SpacingSmall)
            ) {
                items(
                    count = uiState.marketCap.size,
                    key = {
                        uiState.marketCap[it].stock
                    },
                    itemContent = { index ->
                        val stock = uiState.marketCap[index]
                        Column {
                            Badge(
                                modifier = Modifier
                                    .inlineSpacingSmall()
                                    .bottomSpacingVerySmall(),
                                containerColor = MaterialTheme.colorScheme.primary,
                            ) {
                                SugarText(
                                    modifier = Modifier.inlineSpacingSmall(),
                                    text = "Top ${index+1}",
                                    style = MaterialTheme.typography.titleMedium,
                                )
                            }

                            SugarLists.TopStockItem(
                                logo = stock.logo,
                                name = stock.name,
                                code = stock.stock,
                                sector = stock.sector,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .inlineSpacingSmall()
                                    .topSpacingVerySmall(),
                                isLoading = false,
                                onClick = viewModel::openDetails
                            )
                        }
                    },
                )
            }
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}