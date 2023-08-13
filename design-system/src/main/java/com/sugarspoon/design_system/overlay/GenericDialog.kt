package com.sugarspoon.design_system.overlay

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.sugarspoon.design_system.Dimensions
import com.sugarspoon.design_system.Dimensions.bottomSpacing
import com.sugarspoon.design_system.Dimensions.inlineSpacingMedium
import com.sugarspoon.design_system.Dimensions.topSpacing
import com.sugarspoon.design_system.buttons.Buttons
import com.sugarspoon.design_system.text.SugarText

@Composable
fun GenericDialog(
    title: String,
    message: String,
    displayError: Boolean,
    buttonTitlePositive: String,
    buttonTitleNegative: String,
    onActionPositive: () -> Unit,
    onActionNegative: () -> Unit,
) {
    val modalState = rememberOverlayState()
    modalState.handleVisibility(displayError)
    Modal(state = modalState, onDismissListener = {}) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .inlineSpacingMedium()
                .background(MaterialTheme.colorScheme.surfaceVariant, shape = Dimensions.CardShape),
        ) {
            SugarText(
                text = title,
                modifier = Modifier
                    .topSpacing()
                    .fillMaxWidth()
                    .inlineSpacingMedium(),
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            SugarText(
                text = message,
                modifier = Modifier
                    .topSpacing()
                    .fillMaxWidth()
                    .inlineSpacingMedium(),
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            Buttons.Primary(
                modifier = Modifier
                    .fillMaxWidth()
                    .inlineSpacingMedium()
                    .topSpacing()
                    .bottomSpacing(),
                text = buttonTitlePositive,
                onClick = onActionPositive
            )
            Buttons.Secondary(
                modifier = Modifier
                    .fillMaxWidth()
                    .inlineSpacingMedium()
                    .bottomSpacing(),
                text = buttonTitleNegative,
                onClick = onActionNegative
            )
        }
    }
}