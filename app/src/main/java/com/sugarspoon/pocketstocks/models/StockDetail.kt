package com.sugarspoon.pocketstocks.models

data class StockDetail(
    val averageDailyVolume10Day: String = "0",
    val averageDailyVolume3Month: String = "0",
    val currency: String = "",
    val fiftyTwoWeekHigh: Number = 0.0,
    val fiftyTwoWeekHighChange: Number = 0.0,
    val fiftyTwoWeekHighChangePercent: Number = 0.0,
    val fiftyTwoWeekLow: Number = 0.0,
    val fiftyTwoWeekLowChange: Number = 0.0,
    val fiftyTwoWeekLowChangePercent: Number = 0.0,
    val fiftyTwoWeekRange: String = "",
    val longName: String = "",
    val marketCap: String = "0",
    val marketCapNumber: Number = 0,
    val regularMarketChange: String = "0",
    val regularMarketChangeNumber: Number = 0,
    val regularMarketChangePercent: String = "0",
    val regularMarketDayHigh: Number = 0.0,
    val regularMarketDayLow: Number = 0.0,
    val regularMarketDayRange: String = "",
    val regularMarketOpen: Number = 0.0,
    val regularMarketPreviousClose: Number = 0.0,
    val regularMarketPrice: String = "",
    val regularMarketTime: String = "",
    val regularMarketVolume: Number = 0,
    val shortName: String = "",
    val symbol: String = "",
    val historicalDataPrice: List<HistoricalDataPrice?> = listOf(),
    val twoHundredDayAverage: Number = 0.0,
    val twoHundredDayAverageChange: Number = 0.0,
    val twoHundredDayAverageChangePercent: Number = 0.0
)