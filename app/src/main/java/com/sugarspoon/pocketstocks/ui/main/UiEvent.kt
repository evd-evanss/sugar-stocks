package com.sugarspoon.pocketstocks.ui.main

import com.sugarspoon.pocketstocks.base.UiEvents

sealed class UiEvent : UiEvents {
    object LoadStocks : UiEvent()
    object LoadMarketStatus : UiEvent()
    object LoadDataInflation : UiEvent()
    object ClearParams : UiEvent()
    object ClearQuery : UiEvent()
    object LoadStocksPreferences : UiEvent()
    data class SeeQuotDetail(val ticker: String) : UiEvent()
    data class SelectSegment(val segmentOption: String) : UiEvent()
    data class OpenDetails(val code: String, val logo: String) : UiEvent()
    data class SaveStockDetail(val code: String, val logo: String) : UiEvent()
    data class RemoveStockDetailItem(val code: String) : UiEvent()
    data class FindStockInWallet(val code: String) : UiEvent()
    data class OrderListBy(val query: String) : UiEvent()
}