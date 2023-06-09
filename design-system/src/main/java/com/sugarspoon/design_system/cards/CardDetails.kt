package com.sugarspoon.design_system.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sugarspoon.design_system.Dimensions
import com.sugarspoon.design_system.Dimensions.CardShape
import com.sugarspoon.design_system.Dimensions.inlineSpacingMedium
import com.sugarspoon.design_system.text.SugarText

@Composable
fun CardDetails(
    modifier: Modifier,
    headline: String,
    support: String,
    icon: Int,
    iconTint: Color,
    value: String
) {

    Card(
        modifier = modifier.height(CardHeight),
        shape = CardShape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = Dimensions.Level2
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.background(MaterialTheme.colorScheme.surface).weight(1f)
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.inlineSpacingMedium(),
                tint = iconTint
            )
            Column(modifier = Modifier.weight(1f)) {
                SugarText(
                    modifier = Modifier,
                    text = headline,
                    style = MaterialTheme.typography.titleMedium
                )
                SugarText(
                    modifier = Modifier,
                    text = support,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            SugarText(
                modifier = Modifier.inlineSpacingMedium(),
                text = value,
                style = MaterialTheme.typography.titleSmall.copy(color = iconTint)
            )
        }
    }
}

private val CardHeight = 70.dp
