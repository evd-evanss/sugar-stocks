package com.sugarspoon.design_system.bottombar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.size.Dimension
import com.sugarspoon.design_system.Dimensions
import com.sugarspoon.design_system.Dimensions.None

@Composable
fun BottomBar(
    modifier: Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Column(modifier = modifier
        .clickable(enabled = false) {}
        .clip(RoundedCornerShape(PillRadius))
        .background(MaterialTheme.colorScheme.background)
        .fillMaxWidth()) {
        Divider()
        Row(
            modifier = modifier
                .height(BottomBarHeight)
                .background(MaterialTheme.colorScheme.background)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            content(this)
        }
    }

}

private val BottomBarHeight = 60.dp
private val PillRadius = 10.dp
