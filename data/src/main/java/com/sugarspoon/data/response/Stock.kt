package com.sugarspoon.data.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Stock(
    val stock: String? = "",
    val name: String? = "",
    val close: Number? = 0,
    val change: Number? = 0,
    val volume: Number? = 0,
    @SerializedName("market_cap")
    val marketCap: Number? = 0,
    val logo: String? = "",
    val sector: String? = ""
): Serializable
