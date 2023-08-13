package com.sugarspoon.pocketstocks.ui.mappers

import com.sugarspoon.domain.model.local.SummaryStockEntity
import com.sugarspoon.domain.model.remote.BrapiResponseDto
import com.sugarspoon.domain.model.remote.HistoricalDataPriceDto
import com.sugarspoon.domain.model.remote.InflationDto
import com.sugarspoon.domain.model.remote.MarketStatusDto
import com.sugarspoon.domain.model.remote.StockDetailDto
import com.sugarspoon.domain.model.remote.StockDto
import com.sugarspoon.pocketstocks.models.HistoricalDataPrice
import com.sugarspoon.pocketstocks.models.Inflation
import com.sugarspoon.pocketstocks.models.MarketStatus
import com.sugarspoon.pocketstocks.models.Stock
import com.sugarspoon.pocketstocks.models.StockDetail
import com.sugarspoon.pocketstocks.models.SummaryStock
import com.sugarspoon.pocketstocks.utils.addCurrencySuffix
import com.sugarspoon.pocketstocks.utils.asCurrency
import com.sugarspoon.pocketstocks.utils.formatDecimals
import com.sugarspoon.pocketstocks.utils.getDateTime
import java.util.Locale
import javax.inject.Inject

class UiMapper @Inject constructor() {

    fun mapToStockDetail(
        response: StockDetailDto
    ) = StockDetail(
        averageDailyVolume10Day = response.averageDailyVolume10Day?.formatDecimals() ?: "0",
        averageDailyVolume3Month = response.averageDailyVolume3Month?.formatDecimals() ?: "0",
        currency = response.currency.orEmpty(),
        fiftyTwoWeekHigh = response.fiftyTwoWeekHigh ?: 0,
        fiftyTwoWeekHighChange = response.fiftyTwoWeekHighChange ?: 0,
        fiftyTwoWeekHighChangePercent = response.fiftyTwoWeekHighChangePercent ?: 0,
        fiftyTwoWeekLow = response.fiftyTwoWeekLow ?: 0,
        fiftyTwoWeekLowChange = response.fiftyTwoWeekLowChange ?: 0,
        fiftyTwoWeekLowChangePercent = response.fiftyTwoWeekLowChangePercent ?: 0,
        fiftyTwoWeekRange = response.fiftyTwoWeekRange.orEmpty(),
        longName = response.longName.orEmpty(),
        marketCap = response.marketCap?.asCurrency()?.addCurrencySuffix() ?: "0",
        marketCapNumber = response.marketCap ?: 0,
        regularMarketChange = String.format(
            locale = Locale.getDefault(),
            "%.2f",
            response.regularMarketChange?.toDouble()
        ),
        regularMarketChangeNumber = response.regularMarketChange?.toDouble() ?: 0.0,
        regularMarketChangePercent = String.format(
            locale = Locale.getDefault(),
            "%.2f",
            response.regularMarketChangePercent?.toDouble()
        ),
        regularMarketDayHigh = response.regularMarketDayHigh ?: 0,
        regularMarketDayLow = response.regularMarketDayLow ?: 0,
        regularMarketDayRange = response.regularMarketDayRange.orEmpty(),
        regularMarketOpen = response.regularMarketOpen ?: 0,
        regularMarketPreviousClose = response.regularMarketPreviousClose ?: 0,
        regularMarketPrice = response.regularMarketPrice?.toDouble().formatDecimals(),
        regularMarketTime = response.regularMarketTime.orEmpty().getDateTime(),
        regularMarketVolume = response.regularMarketVolume ?: 0,
        shortName = response.shortName.orEmpty(),
        symbol = response.symbol.orEmpty(),
        historicalDataPrice = mapToHistoricalDataPrice(response.historicalDataPrice),
        twoHundredDayAverage = response.twoHundredDayAverage ?: 0,
        twoHundredDayAverageChange = response.twoHundredDayAverageChange ?: 0,
        twoHundredDayAverageChangePercent = response.twoHundredDayAverageChangePercent ?: 0
    )

    fun mapToHistoricalDataPrice(response: List<HistoricalDataPriceDto>?): List<HistoricalDataPrice?> {
        return response?.map { it.map() } ?: listOf()
    }

    private fun HistoricalDataPriceDto.map() : HistoricalDataPrice?{
        return if(openPrice == null && high == null && close == null) {
            null
        } else {
            HistoricalDataPrice(
                adjustedClose = adjustedClose ?: Any(),
                close = close ?: 0L,
                date = date ?: 0,
                high = high ?: 0L,
                low = low ?: 0L,
                openPrice = openPrice ?: 0L,
                volume = volume ?: 0L
            )
        }
    }

    fun mapSummaryStockEntity(response: SummaryStock) = SummaryStockEntity(
        code = response.code,
        name = response.name,
        sector = response.sector,
        logo = response.logo
    )

    private fun mapSummaryStock(response: SummaryStockEntity) = SummaryStock(
        code = response.code,
        name = response.name,
        sector = response.sector,
        logo = response.logo
    )

    fun mapSummaryStockList(response: List<SummaryStockEntity>): List<SummaryStock> {
        return response.map { mapSummaryStock(it) }
    }

    fun mapMarketStatus(response: MarketStatusDto) = MarketStatus(
        message = response.message, isOpened = response.isOpened
    )

    private fun mapStock(response: StockDto) = Stock(
        stock = response.stock.orEmpty(),
        name = response.name.orEmpty(),
        close = response.close ?: 0,
        logo = response.logo.orEmpty(),
        volume = response.volume ?: 0,
        marketCap = response.marketCap ?: 0,
        change = response.change ?: 0,
        sector = response.sector.orEmpty(),
    )

    fun mapStockList(response: BrapiResponseDto): List<Stock> {
        return response.stocks.map { mapStock(it) }
    }

    fun mapInflation(response: InflationDto) = Inflation(
        date = response.date, epochDate = response.epochDate, value = response.value
    )
}