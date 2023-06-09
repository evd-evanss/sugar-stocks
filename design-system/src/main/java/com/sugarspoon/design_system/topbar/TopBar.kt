package com.sugarspoon.design_system.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sugarspoon.design_system.Dimensions.inlineSpacingMedium
import com.sugarspoon.design_system.Dimensions.inlineSpacingSmall
import com.sugarspoon.design_system.buttons.ButtonIcon

@Composable
fun TopBar(
    modifier: Modifier,
    title: String,
    rightDescription: String,
    iconRight: Int,
    onClickRight: (String) -> Unit,
) {
    Row(
        modifier = modifier
            .height(Height)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.weight(1f).inlineSpacingMedium(),
            color = MaterialTheme.colorScheme.onPrimary
        )
        ButtonIcon(
            modifier = Modifier.inlineSpacingSmall(),
            icon = iconRight,
            tabName = rightDescription,
            onClick = onClickRight,
            useAnimation = false
        )
    }
}

private val Height = 70.dp