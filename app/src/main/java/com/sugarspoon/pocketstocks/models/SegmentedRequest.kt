package com.sugarspoon.pocketstocks.models

data class SegmentedRequest(
    val ticker: String,
    val range: String,
    val interval: String = "60m"
)