package com.sugarspoon.pocketfinance.ui.analyze


import android.content.Intent
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sugarspoon.design_system.Dimensions
import com.sugarspoon.design_system.Dimensions.bottomSpacing
import com.sugarspoon.design_system.Dimensions.bottomSpacingVerySmall
import com.sugarspoon.design_system.Dimensions.inlineSpacingMedium
import com.sugarspoon.design_system.Dimensions.inlineSpacingSmall
import com.sugarspoon.design_system.Dimensions.topSpacing
import com.sugarspoon.design_system.Dimensions.topSpacingVerySmall
import com.sugarspoon.design_system.lists.SugarLists
import com.sugarspoon.design_system.overlay.Modal
import com.sugarspoon.design_system.overlay.rememberOverlayState
import com.sugarspoon.design_system.text.SugarText
import com.sugarspoon.design_system.topbar.TopBar
import com.sugarspoon.pocketfinance.ui.details.DetailsActivity
import com.sugarspoon.pocketfinance.ui.home.UiState
import com.sugarspoon.pocketfinance.ui.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyzeScreen(
    viewModel: HomeViewModel,
    stateStockScreen: UiState
) {
    val context = LocalContext.current
    LaunchedEffect(stateStockScreen.code) {
        if (stateStockScreen.code.isNotEmpty() && stateStockScreen.logo.isNotEmpty()) {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra("code", stateStockScreen.code)
            intent.putExtra("logo", stateStockScreen.logo)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(intent)
            viewModel.clearParams()
        }
    }
    val modalState = rememberOverlayState()
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
                    count = stateStockScreen.topStocks.size,
                    key = {
                        stateStockScreen.topStocks[it].stock ?: 0
                    },
                    itemContent = { index ->
                        val stock = stateStockScreen.topStocks[index]
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
                                logo = stock.logo.orEmpty(),
                                name = stock.name.orEmpty(),
                                code = stock.stock.orEmpty(),
                                sector = stock.sector.orEmpty(),
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
                    count = stateStockScreen.topFIIStocks.size,
                    key = {
                        stateStockScreen.topFIIStocks[it].stock ?: 0
                    },
                    itemContent = { index ->
                        val stock = stateStockScreen.topFIIStocks[index]
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
                                logo = stock.logo.orEmpty(),
                                name = stock.name.orEmpty(),
                                code = stock.stock.orEmpty(),
                                sector = stock.sector.orEmpty(),
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
                    count = stateStockScreen.marketCap.size,
                    key = {
                        stateStockScreen.marketCap[it].stock ?: 0
                    },
                    itemContent = { index ->
                        val stock = stateStockScreen.marketCap[index]
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
                                logo = stock.logo.orEmpty(),
                                name = stock.name.orEmpty(),
                                code = stock.stock.orEmpty(),
                                sector = stock.sector.orEmpty(),
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
    modalState.handleVisibility(stateStockScreen.openModal)

    Modal(state = modalState, onDismissListener = {}) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
        ) {
            SugarText(
                text = stateStockScreen.error ?: "Falha na request",
                modifier = Modifier
                    .fillMaxWidth()
                    .inlineSpacingMedium(),
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.titleMedium
            )
            SugarText(
                text = stateStockScreen.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .inlineSpacingMedium(),
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}