package com.sugarspoon.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModelMVI<T : ScreeenState, in E : ScreenEvents>(initialVal: T) : ViewModel() {

    private val _state: MutableStateFlow<T> = MutableStateFlow(initialVal)

    val state: StateFlow<T>
        get() = _state

    fun emitEvent(sideEffect: E) {
        handleEvents(_state.value, sideEffect)
    }

    fun createNewState(newState: T) {
        val success = _state.tryEmit(newState)
    }

    abstract fun handleEvents(oldState: T, sideEffect: E)
}

interface ScreeenState

interface ScreenEvents