package com.sugarspoon.design_system.bottombar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.sugarspoon.design_system.Dimensions.None

@Composable
fun BottomBar(
    modifier: Modifier,
    onSelectTab: (String) -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier
            .height(BottomBarHeight)
            .clip(RoundedCornerShape(PillRadius))
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxSize()
            .border(
                width = None,
                shape = RoundedCornerShape(PillRadius),
                color = MaterialTheme.colorScheme.surface
            ),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        content(this)
    }

}

private val BottomBarHeight = 70.dp
private val PillRadius = (BottomBarHeight / 2)
