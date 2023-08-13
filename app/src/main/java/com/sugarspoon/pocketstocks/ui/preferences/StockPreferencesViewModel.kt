package com.sugarspoon.pocketstocks.ui.preferences

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewModelScope
import com.sugarspoon.domain.usecase.local.DeletePreferenceUseCase
import com.sugarspoon.domain.usecase.local.GetAllPreferenceUseCase
import com.sugarspoon.pocketstocks.base.BaseViewModelMVI
import com.sugarspoon.pocketstocks.ui.mappers.UiMapper
import javax.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class StockPreferencesViewModel @Inject constructor(
    private val deletePreferenceUseCase: DeletePreferenceUseCase,
    private val getAllPreferenceUseCase: GetAllPreferenceUseCase,
    private val uiMapper: UiMapper
) : BaseViewModelMVI<PreferencesUiState>(PreferencesUiState()) {

    override val uiState: @Composable () -> PreferencesUiState
        get() = {
            val uiStateFlow by state.collectAsState()
            uiStateFlow
        }

    private fun fetchWalletStocks() = viewModelScope.launch {
        getAllPreferenceUseCase.invoke()
            .catch {
                createNewState(
                    currentState.value.copy(
                        openModal = true,
                        error = it.message.orEmpty()
                    )
                )
            }
            .onStart {
                createNewState(
                    currentState.value.copy(
                        isLoading = true
                    )
                )
            }
            .collect {
                createNewState(
                    currentState.value.copy(
                        stockPreferences = uiMapper.mapSummaryStockList(it),
                        walletEmptyState = it.isEmpty(),
                        isLoading = false
                    )
                )
            }
    }

    fun openDetails(code: String, logo: String) {
        createNewState(
            newState = currentState.value.copy(
                code = code,
                logo = logo
            )
        )
    }

    fun clearParams() {
        createNewState(
            newState = currentState.value.copy(
                code = "",
                logo = ""
            )
        )
    }

    fun loadStockPreferences() {
        fetchWalletStocks()
    }

    fun onClickUnfollow(code: String) {
        viewModelScope.launch {
            deletePreferenceUseCase.invoke(code)
            fetchWalletStocks()
        }
    }
}

