package com.sugarspoon.pocketstocks.di

import com.sugarspoon.pocketstocks.ui.home.HomeViewModel
import com.sugarspoon.pocketstocks.ui.preferences.StockPreferencesViewModel
import javax.inject.Inject

class ViewModelInjector @Inject constructor(
    val homeViewModel: HomeViewModel,
    val stockPreferencesViewModel: StockPreferencesViewModel
)