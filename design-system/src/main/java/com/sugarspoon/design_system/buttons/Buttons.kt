package com.sugarspoon.design_system.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sugarspoon.design_system.Dimensions
import com.sugarspoon.design_system.Dimensions.inlineSpacingMedium
import com.sugarspoon.design_system.Dimensions.inlineSpacingSmall

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
            modifier = modifier.height(ButtonHeight),
            colors = ButtonDefaults.buttonColors(
                containerColor = backgroundColor
            ),
            shape = Dimensions.ListItemShape
        ) {
            Text(text = text, style = MaterialTheme.typography.titleMedium)
        }
    }

    @Composable
    fun Secondary(
        modifier: Modifier,
        text: String,
        isEnabled: Boolean = true,
        backgroundColor: Color = Color.Transparent,
        onClick: () -> Unit
    ) {
        Button(
            onClick = onClick,
            enabled = isEnabled,
            modifier = modifier.height(ButtonHeight),
            colors = ButtonDefaults.buttonColors(
                containerColor = backgroundColor
            ),
            shape = Dimensions.ListItemShape,
            border = BorderStroke(
                width = Dimensions.BorderStrokeMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onBackground)
            )
        }
    }

    @Composable
    fun FixedButtons(
        modifier: Modifier = Modifier,
        primaryText: String,
        secondaryText: String,
        onClickPrimary: () -> Unit,
        onClickSecondary: () -> Unit,
    ) {
        Row(modifier = modifier
            .fillMaxWidth()
            .inlineSpacingMedium()) {
            Secondary(
                modifier = Modifier
                    .weight(1f)
                    .inlineSpacingSmall(),
                text = secondaryText,
                onClick = onClickSecondary
            )
            Primary(
                modifier = Modifier
                    .weight(1f)
                    .inlineSpacingSmall(),
                text = primaryText,
                onClick = onClickPrimary
            )
        }
    }

    @Composable
    fun PrimarySmall(
        modifier: Modifier,
        text: String,
        isEnabled: Boolean = true,
        backgroundColor: Color = MaterialTheme.colorScheme.primary,
        onClick: () -> Unit
    ) {
        Button(
            onClick = onClick,
            enabled = isEnabled,
            modifier = modifier
                .height(30.dp)
                .width(100.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = backgroundColor
            ),
            shape = Dimensions.ListItemShape,
            contentPadding = PaddingValues(Dimensions.SpacingVerySmall)
        ) {
            Text(text = text, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

private val ButtonHeight = 48.dp
private val SmallButtonHeight = 38.dp