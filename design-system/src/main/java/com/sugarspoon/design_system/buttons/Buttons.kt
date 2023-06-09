package com.sugarspoon.design_system.buttons

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

object Buttons {

    @Composable
    fun Primary(
        modifier: Modifier,
        text: String,
        isEnabled: Boolean = true,
        backgroundColor: Color = MaterialTheme.colorScheme.primary,
        onClick: () -> Unit
    ) {
        Button(
            onClick = onClick,
            enabled = isEnabled,
            modifier = modifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = backgroundColor
            )
        ) {
            Text(text = text, style = MaterialTheme.typography.bodyMedium)
        }
    }
}