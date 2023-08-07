package com.sugarspoon.data.response

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

data class DividendsData(
    val dividendsData: DividendsDataX
)

data class DividendsDataX(
    val cashDividends: List<CashDividend>,
    val stockDividends: List<StockDividend>,
    //val subscriptions: List<Subscription>
)

data class CashDividend(
    val approvedOn: String,
    val assetIssued: String,
    val isinCode: String,
    val label: String,
    val lastDatePrior: String,
    val paymentDate: String,
    val rate: Double,
    val relatedTo: String,
    val remarks: String
)

data class StockDividend(
    val approvedOn: String,
    val assetIssued: String,
    val factor: Double,
    val isinCode: String,
    val label: String,
    val lastDatePrior: String,
    val remarks: String
)

data class Subscription(
    val approvedOn: String,
    val assetIssued: String,
    val isinCode: String,
    val label: String,
    val lastDatePrior: String,
    val percentage: Int,
    val priceUnit: Int,
    val remarks: String,
    val subscriptionDate: String,
    val tradingPeriod: String
)