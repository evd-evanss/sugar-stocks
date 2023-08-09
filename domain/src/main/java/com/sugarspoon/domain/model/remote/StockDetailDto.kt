package com.sugarspoon.domain.model.remote

import java.io.Serializable

data class StockDetailDto(
    val averageDailyVolume10Day: Number?,
    val averageDailyVolume3Month: Number?,
    val currency: String?,
    val fiftyTwoWeekHigh: Number?,
    val fiftyTwoWeekHighChange: Number?,
    val fiftyTwoWeekHighChangePercent: Number?,
    val fiftyTwoWeekLow: Number?,
    val fiftyTwoWeekLowChange: Number?,
    val fiftyTwoWeekLowChangePercent: Number?,
    val fiftyTwoWeekRange: String?,
    val longName: String?,
    val marketCap: Number?,
    val regularMarketChange: Number?,
    val regularMarketChangePercent: Number?,
    val regularMarketDayHigh: Number?,
    val regularMarketDayLow: Number?,
    val regularMarketDayRange: String?,
    val regularMarketOpen: Number?,
    val regularMarketPreviousClose: Number?,
    val regularMarketPrice: Number?,
    val regularMarketTime: String?,
    val regularMarketVolume: Number?,
    val shortName: String?,
    val symbol: String?,
    val historicalDataPrice: List<HistoricalDataPriceDto>?,
    val twoHundredDayAverage: Number?,
    val twoHundredDayAverageChange: Number?,
    val twoHundredDayAverageChangePercent: Number?
): Serializable