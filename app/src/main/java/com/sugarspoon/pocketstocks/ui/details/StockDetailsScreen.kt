package com.sugarspoon.pocketstocks.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.sugarspoon.design_system.Dimensions.bottomSpacing
import com.sugarspoon.design_system.Dimensions.endSpacingMedium
import com.sugarspoon.design_system.Dimensions.inlineSpacingMedium
import com.sugarspoon.design_system.Dimensions.topSpacing
import com.sugarspoon.design_system.buttons.Buttons
import com.sugarspoon.design_system.chart.LineChart
import com.sugarspoon.design_system.overlay.CircularSugarLoading
import com.sugarspoon.design_system.overlay.GenericDialog
import com.sugarspoon.design_system.overlay.SugarLoading
import com.sugarspoon.design_system.overlay.rememberOverlayState
import com.sugarspoon.design_system.segmented.SegmentText
import com.sugarspoon.design_system.segmented.SegmentedControl
import com.sugarspoon.design_system.text.SugarText
import com.sugarspoon.design_system.topbar.TopBar
import com.sugarspoon.pocketfinance.R
import com.sugarspoon.pocketstocks.models.SegmentOptions
import kotlinx.coroutines.launch

@Composable
fun StockDetailsScreen(
    viewModel: DetailsViewModel,
    logo: String,
    onBackPressed: () -> Unit,
    uiState: DetailsUiState,
    code: String
) {
    val overlayState = rememberOverlayState()
    val scrollState = rememberScrollState()
    LaunchedEffect(uiState.detailsLoading) {
        overlayState.handleVisibility(uiState.detailsLoading)
    }

    LaunchedEffect(key1 = code) {
        launch {
            viewModel.getDetail(code)
        }
    }
    LaunchedEffect(key1 = uiState.displaySaveButton) {
        launch {
            viewModel.findStockInWallet(code)
        }
    }
    if (uiState.detailsLoading.not()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(MaterialTheme.colorScheme.background)
        ) {
            TopBar(
                modifier = Modifier,
                title = code,
                rightDescription = "Fechar",
                iconRight = R.drawable.ic_close,
                onClickRight = {
                    onBackPressed()
                    viewModel.clearUiState()
                },
            )

            Row(
                Modifier
                    .fillMaxWidth()
                    .topSpacing(),
                verticalAlignment = Alignment.Top
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(logo)
                        .decoderFactory(SvgDecoder.Factory())
                        .crossfade(true)
                        .size(250)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.inlineSpacingMedium(),
                )
                Column(Modifier.weight(1f)) {
                    SugarText(
                        modifier = Modifier.endSpacingMedium(),
                        text = code,
                        style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.onBackground)
                    )
                    SugarText(
                        modifier = Modifier.endSpacingMedium(),
                        text = uiState.stockDetail.longName,
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground)
                    )
                }
            }
            Box(modifier = Modifier.height(ChartHeight)) {
                CircularSugarLoading(
                    modifier = Modifier.height(ChartHeight),
                    isLoading = uiState.isChartLoading,
                    text = "Carregando dados do gráfico"
                ) {
                    LineChart(
                        data = uiState.historicalDataPrice,
                        graphColor = MaterialTheme.colorScheme.primary,
                        showDashedLine = true,
                        modifier = Modifier
                            .inlineSpacingMedium()
                            .fillMaxWidth()
                            .height(ChartHeight),
                    )
                }
            }
            SegmentedControl(
                modifier = Modifier
                    .inlineSpacingMedium()
                    .topSpacing(),
                segments = SegmentOptions.values().toMutableList(),
                selectedSegment = uiState.selectedSegment,
                onSegmentSelected = viewModel::setSegmentOption
            ) { segmented, isSelected ->
                SegmentText(text = segmented.text, isSelected = isSelected)
            }
            SugarText(
                modifier = Modifier
                    .inlineSpacingMedium()
                    .topSpacing(),
                text = "Última atualização",
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onBackground)
            )
            SugarText(
                modifier = Modifier.inlineSpacingMedium(),
                text = uiState.stockDetail.regularMarketTime,
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.primary)
            )
            SugarText(
                modifier = Modifier
                    .inlineSpacingMedium()
                    .topSpacing(),
                text = "Preço",
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground)
            )
            SugarText(
                modifier = Modifier.inlineSpacingMedium(),
                text = "R$ ${uiState.stockDetail.regularMarketPrice}",
                style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.onBackground)
            )

            SugarText(
                modifier = Modifier
                    .inlineSpacingMedium()
                    .topSpacing(),
                text = "Volume negociado (Média de 10 dias)",
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground)
            )
            SugarText(
                modifier = Modifier.inlineSpacingMedium(),
                text = InfoOrValue(uiState.stockDetail.averageDailyVolume10Day),
                style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.onBackground)
            )
            SugarText(
                modifier = Modifier
                    .inlineSpacingMedium()
                    .topSpacing(),
                text = "Volume negociado (Média de 3 meses)",
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground)
            )
            SugarText(
                modifier = Modifier.inlineSpacingMedium(),
                text = InfoOrValue(uiState.stockDetail.averageDailyVolume3Month),
                style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.onBackground)
            )
            SugarText(
                modifier = Modifier
                    .inlineSpacingMedium()
                    .topSpacing(),
                text = "Variação (dia)",
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground)
            )
            SugarText(
                modifier = Modifier.inlineSpacingMedium(),
                text = uiState.stockDetail.regularMarketChange + " (${uiState.stockDetail.regularMarketChangePercent} %)",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = getTextColor(uiState.stockDetail.regularMarketChangeNumber)
                )
            )
            SugarText(
                modifier = Modifier
                    .inlineSpacingMedium()
                    .topSpacing(),
                text = "Capitalização de mercado",
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground)
            )
            SugarText(
                modifier = Modifier.inlineSpacingMedium(),
                text = uiState.stockDetail.marketCap,
                style = MaterialTheme.typography.titleLarge.copy(color = getTextColor(uiState.stockDetail.marketCapNumber))
            )
            if (uiState.displaySaveButton) {
                Buttons.Primary(
                    modifier = Modifier
                        .fillMaxWidth()
                        .topSpacing()
                        .bottomSpacing()
                        .inlineSpacingMedium(),
                    text = "Incluir na minha lista",
                    onClick = {
                        viewModel.saveStockDetail(code, logo)
                        onBackPressed()
                    }
                )
            }
        }
    }
    SugarLoading(overlayState = overlayState, onDismissListener = onBackPressed)
    GenericDialog(
        title = "Não foi possível recuperar os dados de $code",
        message = uiState.error,
        buttonTitlePositive = "Tentar novamente",
        buttonTitleNegative = "Fechar",
        onActionPositive = {
            viewModel.getDetail(code)
        },
        displayError = uiState.openModal,
        onActionNegative = {
            onBackPressed()
            viewModel.clearUiState()
        }
    )
}

private val ChartHeight = 200.dp
private val InfoOrValue: (String) -> String = { if(it == "0") "Sem informações" else it }

@Composable
private fun getTextColor(number: Number) =
    if (number.toDouble() < 0) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary