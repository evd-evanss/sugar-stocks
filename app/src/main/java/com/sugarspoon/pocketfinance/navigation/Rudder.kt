package com.sugarspoon.pocketfinance.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sugarspoon.home.HomeScreen
import com.sugarspoon.pocketfinance.ui.ChartScreen
import com.sugarspoon.pocketfinance.ui.WalletScreen

fun NavGraphBuilder.homeScreen() {
    composable(route = Screens.Home.name) {
        HomeScreen()
    }
    composable(route = Screens.Analysis.name) {
        ChartScreen()
    }
    composable(route = Screens.Wallet.name) {
        Column {
            WalletScreen()
        }
    }
    composable(route = Screens.Profile.name) {
        Column {
            Text(text = "Perfil")
        }
    }
}