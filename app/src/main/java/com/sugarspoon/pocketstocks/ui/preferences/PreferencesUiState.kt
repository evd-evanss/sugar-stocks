package com.sugarspoon.pocketstocks.ui.preferences

import com.sugarspoon.pocketstocks.base.UiStates
import com.sugarspoon.pocketstocks.models.SummaryStock

data class PreferencesUiState(
    val error: String = "",
    val openModal: Boolean = false,
    val code: String = "",
    val logo: String = "",
    val stockPreferences: List<SummaryStock> = listOf(),
    val walletEmptyState: Boolean = true,
    val isLoading: Boolean = false
) : UiStates