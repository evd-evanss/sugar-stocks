package com.sugarspoon.pocketstocks.models

import java.io.Serializable

data class Stock(
    val stock: String,
    val name: String,
    val close: Number,
    val change: Number,
    val volume: Number,
    val marketCap: Number,
    val logo: String,
    val sector: String
): Serializable
