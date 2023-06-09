package com.sugarspoon.design_system.overlay

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun Modal(
    state: OverlayState,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    if (state.isVisible) {
        Dialog(
            onDismissRequest = {},
            properties = DialogProperties(usePlatformDefaultWidth = false),
        ) {
            Column(
                modifier = modifier,
                content = content,
            )
        }
    }
}

@Composable
fun rememberOverlayState() = remember {
    OverlayState()
}

class OverlayState {

    var isVisible by mutableStateOf(false)
        private set

    fun display() {
        isVisible = true
    }

    fun hide() {
        isVisible = false
    }

    fun handleVisibility(isDisplay: Boolean) {
        if (isDisplay) {
            display()
        } else {
            hide()
        }
    }
}
