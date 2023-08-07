package com.sugarspoon.design_system.chart

import androidx.compose.runtime.Stable

@Stable
data class LinearChartEntry(
    val date: String,
    val value: Float
)