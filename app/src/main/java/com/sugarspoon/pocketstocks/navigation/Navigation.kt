package com.sugarspoon.pocketstocks.navigation

import androidx.compose.foundation.layout.Column
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sugarspoon.pocketstocks.di.ViewModelInjector
import com.sugarspoon.pocketstocks.ui.analyze.AnalyzeScreen
import com.sugarspoon.pocketstocks.ui.home.HomeScreen
import com.sugarspoon.pocketstocks.ui.home.HomeUiState
import com.sugarspoon.pocketstocks.ui.preferences.StockPreferencesScreen
import com.sugarspoon.pocketstocks.ui.preferences.PreferencesUiState

fun NavGraphBuilder.navigation(
    viewModels: ViewModelInjector,
    homeUiState: HomeUiState,
    onFinish: () -> Unit
) {
    composable(route = Screens.Home.name) {
        HomeScreen(
            viewModel = viewModels.homeViewModel,
            onFinish = onFinish,
            uiState = homeUiState
        )
    }
    composable(route = Screens.Analysis.name) {
        AnalyzeScreen(
            viewModel = viewModels.homeViewModel,
            uiState = homeUiState
        )
    }
    composable(route = Screens.Wallet.name) {
        Column {
            StockPreferencesScreen(
                viewModel = viewModels.stockPreferencesViewModel,
            )
        }
    }
}