package com.sugarspoon.pocketstocks.base

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModelMVI<T : UiStates>(initialVal: T) : ViewModel() {

    protected val currentState: MutableStateFlow<T> = MutableStateFlow(initialVal)

    val state: StateFlow<T>
        get() = currentState

    abstract val uiState: @Composable () -> T

    fun createNewState(newState: T) {
        currentState.tryEmit(newState)
    }
}

interface UiStates

interface UiEvents