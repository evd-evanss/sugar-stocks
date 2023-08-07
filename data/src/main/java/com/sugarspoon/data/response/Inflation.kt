package com.sugarspoon.data.response

data class InflationDTO(val inflation: List<Inflation>)

data class Inflation(
    val date: String = "",
    val epochDate: Number = 0,
    val value: String= ""
)