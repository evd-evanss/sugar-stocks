package com.sugarspoon.pocketfinance.ui.details

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.sugarspoon.design_system.Dimensions
import com.sugarspoon.design_system.Dimensions.bottomSpacing
import com.sugarspoon.design_system.Dimensions.endSpacingMedium
import com.sugarspoon.design_system.Dimensions.inlineSpacingMedium
import com.sugarspoon.design_system.Dimensions.topSpacing
import com.sugarspoon.design_system.buttons.Buttons
import com.sugarspoon.design_system.chart.LineChart
import com.sugarspoon.design_system.overlay.CircularSugarLoading
import com.sugarspoon.design_system.overlay.SugarLoading
import com.sugarspoon.design_system.overlay.rememberOverlayState
import com.sugarspoon.design_system.segmented.SegmentText
import com.sugarspoon.design_system.segmented.SegmentedControl
import com.sugarspoon.design_system.text.SugarText
import com.sugarspoon.design_system.topbar.TopBar
import com.sugarspoon.pocketfinance.R
import com.sugarspoon.pocketfinance.ui.home.HomeViewModel
import com.sugarspoon.pocketfinance.ui.home.SegmentOptions
import com.sugarspoon.pocketfinance.ui.home.SegmentedRequest
import com.sugarspoon.pocketfinance.ui.home.UiState
import com.sugarspoon.pocketfinance.utils.formatWith2DecimalPlaces
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone
import kotlinx.coroutines.launch

@Composable
fun StockDetails(
    viewModel: HomeViewModel,
    logo: String,
    onBackPressed: () -> Unit,
    code: String,
    state: UiState
) {
    val overlayState = rememberOverlayState()
    val scrollState = rememberScrollState()
    LaunchedEffect(state.detailsLoading) {
        overlayState.handleVisibility(state.detailsLoading)
    }

    LaunchedEffect(key1 = code) {
        launch {
            viewModel.getDetail(code)
        }
    }
    LaunchedEffect(key1 = state.displaySaveButton) {
        launch {
            viewModel.findStockInWallet(code)
        }
    }
    if(state.detailsLoading.not()) {
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
                        text = state.stockDetail.longName,
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground)
                    )
                }
            }
            Box(modifier = Modifier.height(ChartHeight)) {
                CircularSugarLoading(
                    modifier = Modifier.height(ChartHeight),
                    isLoading = state.isChartLoading,
                    text = "Carregando dados do gráfico"
                ) {
                    LineChart(
                        data = state.historicalDataPrice,
                        graphColor = MaterialTheme.colorScheme.primary,
                        showDashedLine = true,
                        modifier = Modifier.inlineSpacingMedium().fillMaxWidth().height(ChartHeight)
                    )
                }
            }
            SegmentedControl(
                modifier = Modifier.inlineSpacingMedium().topSpacing(),
                segments = SegmentOptions.values().toMutableList(),
                selectedSegment = state.selectedSegment,
                onSegmentSelected = viewModel::setSegmentOption
            ) { segmented , isSelected->
                SegmentText(text = segmented.text, isSelected = isSelected)
            }
            SugarText(
                modifier = Modifier.inlineSpacingMedium().topSpacing(),
                text = "Última atualização",
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onBackground)
            )
            SugarText(
                modifier = Modifier.inlineSpacingMedium(),
                text = state.stockDetail.regularMarketTime.getDateTime(),
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.primary)
            )
            SugarText(
                modifier = Modifier.inlineSpacingMedium().topSpacing(),
                text = "Preço",
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground)
            )
            SugarText(
                modifier = Modifier.inlineSpacingMedium(),
                text = state.stockDetail.regularMarketPrice.asCurrency(),
                style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.onBackground)
            )

            SugarText(
                modifier = Modifier.inlineSpacingMedium().topSpacing(),
                text = "Volume negociado (Média de 10 dias)",
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground)
            )
            SugarText(
                modifier = Modifier.inlineSpacingMedium(),
                text = state.stockDetail.averageDailyVolume10Day.toDouble().formatWith2DecimalPlaces(),
                style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.onBackground)
            )
            SugarText(
                modifier = Modifier.inlineSpacingMedium().topSpacing(),
                text = "Volume negociado (Média de 3 meses)",
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground)
            )
            SugarText(
                modifier = Modifier.inlineSpacingMedium(),
                text = state.stockDetail.averageDailyVolume3Month.toDouble().formatWith2DecimalPlaces(),
                style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.onBackground)
            )
            SugarText(
                modifier = Modifier.inlineSpacingMedium().topSpacing(),
                text = "Variação (dia)",
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground)
            )
            SugarText(
                modifier = Modifier.inlineSpacingMedium(),
                text = state.stockDetail.regularMarketChange.asCurrency() + " (${
                    String.format(
                        locale = Locale.getDefault(),
                        "%.2f",
                        state.stockDetail.regularMarketChangePercent.toDouble()
                    )
                } %)",
                style = MaterialTheme.typography.titleLarge.copy(color = getTextColor(state.stockDetail.regularMarketChange))
            )
            SugarText(
                modifier = Modifier.inlineSpacingMedium().topSpacing(),
                text = "Capitalização de mercado",
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground)
            )
            SugarText(
                modifier = Modifier.inlineSpacingMedium(),
                text = state.stockDetail.marketCap.asCurrency().addCurrencySuffix(),
                style = MaterialTheme.typography.titleLarge.copy(color = getTextColor(state.stockDetail.marketCap))
            )
            if(state.displaySaveButton) {
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
}

private val ChartHeight = 200.dp

@Composable
private fun getTextColor(number: Number) =
    if (number.toDouble() < 0) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary

private fun String.getDateTime(): String {
    return if(this.isEmpty()) this
    else try {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        val value = formatter.parse(this)
        val dateFormatter = SimpleDateFormat("dd/MM/yyyy - hh:mm a", Locale.getDefault())
        dateFormatter.timeZone = TimeZone.getDefault()
        dateFormatter.format(value)
    } catch (e: Exception) {
        "00/00/0000 - 00:00"
    }
}

@SuppressLint("ConstantLocale")
private val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.getDefault()).configure()

private fun NumberFormat.configure() = apply {
    maximumFractionDigits = 2
    minimumFractionDigits = 2
}

fun Number.asCurrency(): String {
    return currencyFormatter.format(this)
}

fun String.addCurrencySuffix(): String {
    val points = this.fold(0) { acc, c ->
        if(c == '.') acc + 1 else acc + 0
    }
    return when (points) {
        2 -> this.split(".").first() + " mi"
        3 -> this.split(".").first() + " bi"
        4 -> this.split(".").first() + " tri"
        else -> this
    }
}

fun String.formatToCurrency(): String {
    return format(locale = Locale.getDefault(), "%.2f").replace(".", ",")
}

fun Number?.formatToPercentage(): String {
    return if(this != null) String.format( "%.2f",this) else ""
}