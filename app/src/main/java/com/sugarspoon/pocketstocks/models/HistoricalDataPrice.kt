package com.sugarspoon.pocketstocks.models

data class HistoricalDataPrice(
    val adjustedClose: Any,
    val close: Number,
    val date: Int,
    val high: Number,
    val low: Number,
    val openPrice: Number,
    val volume: Number
)