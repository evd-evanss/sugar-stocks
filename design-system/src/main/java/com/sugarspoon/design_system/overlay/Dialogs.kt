package com.sugarspoon.design_system.overlay

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.sugarspoon.design_system.Dimensions
import com.sugarspoon.design_system.Dimensions.bottomSpacing
import com.sugarspoon.design_system.Dimensions.inlineSpacingMedium
import com.sugarspoon.design_system.Dimensions.topSpacing
import com.sugarspoon.design_system.buttons.Buttons
import com.sugarspoon.design_system.text.SugarText

@Composable
fun SugarDialog(
    isVisible: Boolean,
    title: String,
    message: String,
    onClickRight: () -> Unit,
    onClickLeft: () -> Unit,
) {
    if (isVisible) {
        Dialog(
            onDismissRequest = onClickLeft,
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnBackPress = true
            ),
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .inlineSpacingMedium().inlineSpacingMedium()
                    .background(MaterialTheme.colorScheme.surfaceVariant, Dimensions.CardShape),
            ) {
                SugarText(
                    modifier = Modifier
                        .topSpacing()
                        .inlineSpacingMedium(),
                    text = title,
                    textAlign = TextAlign.Left,
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                SugarText(
                    modifier = Modifier
                        .inlineSpacingMedium(),
                    text = message,
                    textAlign = TextAlign.Left,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
                Buttons.FixedButtons(
                    modifier = Modifier.topSpacing().bottomSpacing(),
                    primaryText = "SIM",
                    secondaryText = "NÃO",
                    onClickSecondary = onClickLeft,
                    onClickPrimary = onClickRight
                )
            }
        }
    }
}