package com.sugarspoon.pocketfinance.navigation

object Router {

    val Routes: (routeId: Int) -> String = { routeId ->
        when(routeId) {
            Screens.Home.id -> Screens.Home.name
            Screens.Analysis.id -> Screens.Analysis.name
            Screens.Wallet.id -> Screens.Wallet.name
            Screens.Profile.id -> Screens.Profile.name
            else -> Screens.Home.name
        }
    }
}