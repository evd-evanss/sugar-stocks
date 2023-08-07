package com.sugarspoon.pocketfinance.ui.mappers

import com.sugarspoon.data.response.HistoricalDataPriceDto
import com.sugarspoon.data.response.StockDetailDto
import com.sugarspoon.pocketfinance.models.HistoricalDataPrice
import com.sugarspoon.pocketfinance.models.StockDetail
import javax.inject.Inject

class UiMapper @Inject constructor() {

    fun mapToStockDetail(
        response: StockDetailDto
    ) = StockDetail(
        averageDailyVolume10Day = response.averageDailyVolume10Day ?: 0,
        averageDailyVolume3Month = response.averageDailyVolume3Month ?: 0,
        currency = response.currency.orEmpty(),
        fiftyTwoWeekHigh = response.fiftyTwoWeekHigh?: 0,
        fiftyTwoWeekHighChange = response.fiftyTwoWeekHighChange ?: 0,
        fiftyTwoWeekHighChangePercent = response.fiftyTwoWeekHighChangePercent ?: 0,
        fiftyTwoWeekLow = response.fiftyTwoWeekLow ?: 0,
        fiftyTwoWeekLowChange = response.fiftyTwoWeekLowChange ?: 0,
        fiftyTwoWeekLowChangePercent = response.fiftyTwoWeekLowChangePercent ?: 0,
        fiftyTwoWeekRange = response.fiftyTwoWeekRange.orEmpty(),
        longName = response.longName.orEmpty(),
        marketCap = response.marketCap ?: 0,
        regularMarketChange = response.regularMarketChange ?: 0,
        regularMarketChangePercent = response.regularMarketChangePercent ?: 0,
        regularMarketDayHigh = response.regularMarketDayHigh ?: 0,
        regularMarketDayLow = response.regularMarketDayLow ?: 0,
        regularMarketDayRange = response.regularMarketDayRange.orEmpty(),
        regularMarketOpen = response.regularMarketOpen ?: 0,
        regularMarketPreviousClose = response.regularMarketPreviousClose ?: 0,
        regularMarketPrice = response.regularMarketPrice ?: 0,
        regularMarketTime = response.regularMarketTime.orEmpty(),
        regularMarketVolume = response.regularMarketVolume ?: 0,
        shortName = response.shortName.orEmpty(),
        symbol = response.symbol.orEmpty(),
        historicalDataPrice = mapToHistoricalDataPrice(response.historicalDataPrice),
        twoHundredDayAverage = response.twoHundredDayAverage ?: 0,
        twoHundredDayAverageChange = response.twoHundredDayAverageChange?: 0,
        twoHundredDayAverageChangePercent = response.twoHundredDayAverageChangePercent?: 0
    )

    fun mapToHistoricalDataPrice(response: List<HistoricalDataPriceDto>?): List<HistoricalDataPrice> {
        return response?.map { it.map() } ?: listOf()
    }

    private fun HistoricalDataPriceDto.map() = HistoricalDataPrice(
        adjustedClose = adjustedClose ?: Any(),
        close = close ?: 0L,
        date = date ?: 0,
        high = high ?: 0L,
        low = low ?: 0L,
        openPrice = openPrice ?: 0L,
        volume = volume ?: 0L
    )
}