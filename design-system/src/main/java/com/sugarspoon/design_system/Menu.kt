package com.sugarspoon.design_system

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sugarspoon.design_system.Dimensions.inlineSpacingSmall
import com.sugarspoon.design_system.text.SugarText
import com.sugarspoon.design_system.icons.SugarSpoonIcons

@Composable
fun OverflowMenu(
    onDismissRequest: () -> Unit,
    onClickMore: () -> Unit,
    showMenu: Boolean,
    onClickSearch: () -> Unit,
    onChangeVisibility: (Boolean) -> Unit
) {
    Box(
        contentAlignment = Alignment.CenterEnd
    ) {
        IconButton(onClick = onClickMore) {
            Icon(
                painter = painterResource(id = SugarSpoonIcons.Outline.Menu1),
                contentDescription = "Opções",
                tint = MaterialTheme.colorScheme.primary,
            )
        }
        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = onDismissRequest,
            modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            DropdownMenuItem(
                onClick = {
                    onChangeVisibility(showMenu.not())
                    onClickSearch()
                },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = SugarSpoonIcons.Outline.Search),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        SugarText(
                            text = "Filtrar",
                            modifier = Modifier.inlineSpacingSmall(),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            )
        }
    }
}