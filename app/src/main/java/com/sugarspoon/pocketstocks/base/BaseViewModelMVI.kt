package com.sugarspoon.pocketstocks.base

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModelMVI<T : UiStates, in E : UiEvents>(initialVal: T) : ViewModel() {

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

    abstract fun handleEvents(oldState: T, event: E)
}

interface UiStates

interface UiEvents