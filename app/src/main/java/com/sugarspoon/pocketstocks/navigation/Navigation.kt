package com.sugarspoon.pocketstocks.navigation

import androidx.compose.foundation.layout.Column
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sugarspoon.pocketstocks.ui.home.HomeScreen
import com.sugarspoon.pocketstocks.ui.main.UiState
import com.sugarspoon.pocketstocks.ui.main.MainViewModel
import com.sugarspoon.pocketstocks.ui.analyze.AnalyzeScreen
import com.sugarspoon.pocketstocks.ui.list.MyListScreen

fun NavGraphBuilder.navigation(
    viewModel: MainViewModel,
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