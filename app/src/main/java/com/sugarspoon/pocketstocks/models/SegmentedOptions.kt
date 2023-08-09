package com.sugarspoon.pocketstocks.models

enum class SegmentOptions(val option: String, val text: String, val interval: String) {
    ONE_DAY(option = "1d", text = "1D", interval = "60m"),
    FIVE_DAY(option = "5d", text = "5D", interval = "60m"),
    ONE_MONTH(option = "1mo", text = "1M", interval = "60m"),
    THREE_MONTH(option = "3mo", text = "3M", interval = "60m"),
    SIX_MONTH(option = "6mo", text = "6M", interval = "60m"),
    ONE_YEAR(option = "1y", text = "1A", interval = "60m"),
    TWO_YEAR(option = "2y", text = "2A", interval = "60m"),
    FIVE_YEAR(option = "5y", text = "5A", interval = "1mo"), ;
}