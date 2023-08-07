package com.sugarspoon.design_system.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.sugarspoon.design_system.Dimensions.inlineSpacingMedium
import com.sugarspoon.design_system.Dimensions.inlineSpacingSmall
import com.sugarspoon.design_system.Dimensions.topSpacing
import com.sugarspoon.design_system.OverflowMenu
import com.sugarspoon.design_system.buttons.ButtonIcon

@Composable
fun TopBar(
    modifier: Modifier,
    title: String,
    rightDescription: String,
    iconRight: Int,
    onClickRight: (String) -> Unit,
    showMenuOptions: Boolean = false,
    onClickSearch: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .height(Height)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .semantics { heading() }
                .weight(1f)
                .inlineSpacingMedium(),
            color = MaterialTheme.colorScheme.primary
        )

        if(showMenuOptions) {
            val showMenu = remember { mutableStateOf(false) }
            OverflowMenu(
                onDismissRequest = { showMenu.value = false },
                onClickMore = { showMenu.value = true },
                showMenu = showMenu.value,
                onClickSearch = onClickSearch,
                onChangeVisibility = { showMenu.value = it }
            )
        } else {
            ButtonIcon(
                modifier = Modifier.inlineSpacingSmall(),
                icon = iconRight,
                tabName = rightDescription,
                onClick = onClickRight,
                useAnimation = false,
                iconColor = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
fun TopBar(
    modifier: Modifier,
    title: String
) {
    Row(
        modifier = modifier
            .topSpacing()
            .height(Height)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .semantics { heading() }
                .weight(1f)
                .inlineSpacingMedium(),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

private val Height = 50.dp