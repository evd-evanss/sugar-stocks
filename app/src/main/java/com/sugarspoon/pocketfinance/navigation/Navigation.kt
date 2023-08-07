package com.sugarspoon.pocketfinance.navigation

import androidx.compose.foundation.layout.Column
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sugarspoon.pocketfinance.ui.home.HomeScreen
import com.sugarspoon.pocketfinance.ui.home.UiState
import com.sugarspoon.pocketfinance.ui.home.HomeViewModel
import com.sugarspoon.pocketfinance.ui.analyze.AnalyzeScreen
import com.sugarspoon.pocketfinance.ui.list.MyListScreen

fun NavGraphBuilder.navigation(
    viewModel: HomeViewModel,
    stateStockScreen: UiState
) {
    composable(route = Screens.Home.name) {
        HomeScreen(
            viewModel = viewModel,
            uiState = stateStockScreen
        )
    }
    composable(route = Screens.Analysis.name) {
        AnalyzeScreen(
            viewModel = viewModel,
            stateStockScreen = stateStockScreen
        )
    }
    composable(route = Screens.Wallet.name) {
        Column {
            MyListScreen(
                viewModel = viewModel,
            )
        }
    }
}