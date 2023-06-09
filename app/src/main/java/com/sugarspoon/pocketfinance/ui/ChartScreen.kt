package com.sugarspoon.pocketfinance.ui


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sugarspoon.design_system.Dimensions.bottomSpacing
import com.sugarspoon.design_system.Dimensions.inlineSpacingMedium
import com.sugarspoon.design_system.Dimensions.topSpacing
import com.sugarspoon.design_system.chart.ChartBar
import com.sugarspoon.design_system.chart.ChartEntry
import com.sugarspoon.design_system.theme.BloodLight
import com.sugarspoon.design_system.theme.Olivine

@Composable
fun ChartScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        ChartBar(
            modifier = Modifier
                .inlineSpacingMedium()
                .topSpacing()
                .bottomSpacing(),
            entries = listOf(
                ChartEntry(
                    tag = "Receita",
                    value = 20000f,
                    background = Olivine
                ),
                ChartEntry(
                    tag = "Despesas",
                    value = 10000f,
                    background = BloodLight
                ),
            ),
            limit = 30000f,
            background = MaterialTheme.colorScheme.onBackground
        )
    }
}