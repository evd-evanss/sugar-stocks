package com.sugarspoon.design_system.overlay

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.sugarspoon.design_system.Dimensions.inlineSpacingMedium
import com.sugarspoon.design_system.Dimensions.topSpacing
import com.sugarspoon.design_system.text.SugarText

@Composable
fun SugarLoading(
    overlayState: OverlayState,
    text: String = "Carregando...",
    onDismissListener: () -> Unit = {}
) {
    if (overlayState.isVisible) {
        Dialog(
            onDismissRequest = onDismissListener,
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnBackPress = true
            ),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(LoadingSize),
                        strokeWidth = StrokeWidth
                    )
                    SugarText(
                        modifier = Modifier.topSpacing(),
                        text = text,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun CircularSugarLoading(
    isLoading: Boolean,
    text: String,
    modifier: Modifier,
    content: @Composable () -> Unit
) {
    if (isLoading) {
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(SmallLoadingSize),
                strokeWidth = StrokeWidth
            )
            SugarText(
                modifier = Modifier
                    .inlineSpacingMedium()
                    .topSpacing(),
                text = text,
                style = MaterialTheme.typography.bodyMedium.copy(MaterialTheme.colorScheme.onBackground)
            )
        }
    } else {
        content()
    }
}

private val LoadingSize = 72.dp
private val SmallLoadingSize = 32.dp
private val StrokeWidth = 4.dp