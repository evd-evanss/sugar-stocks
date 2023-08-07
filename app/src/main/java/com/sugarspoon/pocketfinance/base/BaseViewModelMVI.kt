package com.sugarspoon.pocketfinance.base

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModelMVI<T : ScreeenState, in E : ScreenEvents>(initialVal: T) : ViewModel() {

    private val _state: MutableStateFlow<T> = MutableStateFlow(initialVal)

    val state: StateFlow<T>
        get() = _state

    abstract val uiState: @Composable () -> T

    fun emitEvent(vararg event: E) {
        event.forEach { handleEvents(_state.value, it) }
    }

    fun createNewState(newState: T) {
        _state.tryEmit(newState)
    }

    abstract fun handleEvents(oldState: T, sideEffect: E)
}

interface ScreeenState

interface ScreenEvents