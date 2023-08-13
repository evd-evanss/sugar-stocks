package com.sugarspoon.pocketstocks.ui.preferences

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sugarspoon.design_system.Dimensions
import com.sugarspoon.design_system.Dimensions.bottomSpacing
import com.sugarspoon.design_system.Dimensions.inlineSpacingMedium
import com.sugarspoon.design_system.Dimensions.topSpacing
import com.sugarspoon.design_system.Dimensions.topSpacingSmall
import com.sugarspoon.design_system.Dimensions.topSpacingVerySmall
import com.sugarspoon.design_system.icons.SugarSpoonIcons
import com.sugarspoon.design_system.lists.SugarLists
import com.sugarspoon.design_system.lists.skeleton
import com.sugarspoon.design_system.text.SugarText
import com.sugarspoon.design_system.topbar.TopBar
import com.sugarspoon.pocketstocks.ui.details.DetailsActivity.Companion.getDetailsActivityIntent
import kotlinx.coroutines.launch

@Composable
fun StockPreferencesScreen(
    viewModel: StockPreferencesViewModel
) {
    val uiState = viewModel.uiState.invoke()
    LaunchedEffect(uiState.stockPreferences.size) {
        launch {
            viewModel.loadStockPreferences()
        }
    }
    val context = LocalContext.current
    LaunchedEffect(uiState.code) {
        if (uiState.code.isNotEmpty() && uiState.logo.isNotEmpty()) {
            context.startActivity(
                context.getDetailsActivityIntent(
                    code = uiState.code,
                    logo = uiState.logo
                )
            )
            viewModel.clearParams()
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            title = "Minha Lista",
            modifier = Modifier
        )
        if (uiState.walletEmptyState) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = SugarSpoonIcons.Outline.Wallet),
                    contentDescription = null,
                    modifier = Modifier
                        .size(150.dp)
                        .rotate(-45f),
                    tint = MaterialTheme.colorScheme.onBackground
                )
                SugarText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .inlineSpacingMedium()
                        .bottomSpacing()
                        .bottomSpacing(),
                    text = "Hmm.. está vazia, que tal incluir alguns ativos...",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                userScrollEnabled = true
            ) {
                if (uiState.isLoading) {
                    items(
                        count = 10,
                        itemContent = { index ->
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .topSpacingSmall()
                                    .skeleton(true, shape = Dimensions.ListItemShape)
                            )
                        },
                    )
                } else {
                    items(
                        count = uiState.stockPreferences.size,
                        key = {
                            uiState.stockPreferences[it].code ?: 0
                        },
                        itemContent = { index ->
                            val stock = uiState.stockPreferences[index]
                            val bottomModifier =
                                if (index == uiState.stockPreferences.lastIndex) {
                                    Modifier.padding(bottom = 100.dp)
                                } else {
                                    Modifier
                                }
                            if (index == 0) {
                                SugarText(
                                    modifier = Modifier
                                        .inlineSpacingMedium()
                                        .bottomSpacing()
                                        .topSpacing(),
                                    text = "SEGUINDO",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                )
                            }
                            SugarLists.SimpleStockItem(
                                logo = stock.logo,
                                name = stock.name,
                                code = stock.code,
                                modifier = Modifier
                                    .then(bottomModifier)
                                    .topSpacingVerySmall()
                                    .inlineSpacingMedium(),
                                onClick = viewModel::openDetails,
                                onClickUnfollow = viewModel::onClickUnfollow
                            )
                        },
                    )
                }
            }
        }
    }
}