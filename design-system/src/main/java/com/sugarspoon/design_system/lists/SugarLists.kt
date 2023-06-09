package com.sugarspoon.design_system.lists

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sugarspoon.design_system.Dimensions.inlineSpacingMedium
import com.sugarspoon.design_system.text.SugarText
import com.sugarspoon.icons.generated.SugarSpoonIcons

object SugarLists {

    @Composable
    fun Simple(
        headline: String,
        support: String,
        icon: Int,
        iconTint: Color,
        value: String,
        background: Color = MaterialTheme.colorScheme.surface,
        modifier: Modifier
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(background)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.height(ItemHeight)
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
            Divider(
                color = MaterialTheme.colorScheme.onPrimary,
                thickness = 2.dp
            )
        }
    }

    @Composable
    fun Founds(
        code: String,
        cnpj: String,
        fantasyName: String,
        background: Color = MaterialTheme.colorScheme.surface,
        modifier: Modifier,
        onClick: (Int) -> Unit
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(background)
                .clickable(
                    onClick = {
                        onClick(code.toInt())
                    }
                )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.height(ItemHeight)
            ) {
                Icon(
                    painter = painterResource(id = SugarSpoonIcons.Outline.Dollar),
                    contentDescription = null,
                    modifier = Modifier.inlineSpacingMedium(),
                )
                Column(modifier = Modifier.weight(1f)) {
                    SugarText(
                        modifier = Modifier,
                        text = "CNPJ",
                        style = MaterialTheme.typography.titleMedium
                    )
                    SugarText(
                        modifier = Modifier,
                        text = cnpj,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    SugarText(
                        modifier = Modifier,
                        text = "Nome Fantasia",
                        style = MaterialTheme.typography.titleMedium
                    )
                    SugarText(
                        modifier = Modifier,
                        text = fantasyName,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
//                SugarText(
//                    modifier = Modifier.inlineSpacingMedium(),
//                    text = value,
//                    style = MaterialTheme.typography.titleSmall
//                )
            }
            Divider(
                color = MaterialTheme.colorScheme.onPrimary,
                thickness = 2.dp
            )
        }
    }
}

data class Fund(
    val code: String,
    val fantasyName: String,
    val cnpj: String,
    val type: String,
    val status: String, //Ativo ou Encerrado
    val initialDate: String, //YYYY-MM-DD,
    val lastUpdate: String, //YYYY-MM-DD HH:MI:SS
)

private val ItemHeight = 120.dp