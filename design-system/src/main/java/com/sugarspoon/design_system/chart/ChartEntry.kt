package com.sugarspoon.design_system.chart

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

@Stable
data class ChartEntry(
    val tag: String,
    val value: Float,
    val background: Color
)