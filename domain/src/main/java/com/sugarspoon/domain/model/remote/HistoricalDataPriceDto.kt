package com.sugarspoon.domain.model.remote

import com.google.gson.annotations.SerializedName

data class HistoricalDataPriceDto(
    val adjustedClose: Any?,
    val close: Number?,
    val date: Int?,
    val high: Number?,
    val low: Number?,
    @SerializedName("open")
    val openPrice: Number?,
    val volume: Number?
)
