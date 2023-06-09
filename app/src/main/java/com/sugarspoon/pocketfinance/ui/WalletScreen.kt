package com.sugarspoon.pocketfinance.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sugarspoon.design_system.Dimensions.inlineSpacingMedium
import com.sugarspoon.design_system.Dimensions.topSpacing
import com.sugarspoon.design_system.buttons.Buttons
import com.sugarspoon.design_system.cards.CardDetails
import com.sugarspoon.design_system.theme.BloodLight
import com.sugarspoon.design_system.theme.Olivine
import com.sugarspoon.icons.generated.SugarSpoonIcons

@Composable
fun WalletScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        CardDetails(
            modifier = Modifier.topSpacing().inlineSpacingMedium(),
            headline = "Suas receitas",
            support = "56%",
            icon = SugarSpoonIcons.Outline.ArrowTop1,
            value = "R$ 1200,00",
            iconTint = Olivine
        )
        CardDetails(
            modifier = Modifier.topSpacing().inlineSpacingMedium(),
            headline = "Suas despesas",
            support = "56%",
            icon = SugarSpoonIcons.Outline.ArrowBottom2,
            value = "R$ 1200,00",
            iconTint = BloodLight
        )

        Buttons.Primary(
            modifier = Modifier.fillMaxWidth().topSpacing().inlineSpacingMedium(),
            text = "Cadastrar Receita",
            onClick = {}
        )

        Buttons.Primary(
            modifier = Modifier.fillMaxWidth().topSpacing().inlineSpacingMedium(),
            text = "Cadastrar Despesa",
            backgroundColor = BloodLight,
            onClick = {}
        )
    }
}