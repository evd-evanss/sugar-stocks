package com.sugarspoon.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sugarspoon.design_system.Dimensions.inlineSpacingMedium
import com.sugarspoon.design_system.Dimensions.topSpacing
import com.sugarspoon.design_system.lists.SugarLists
import com.sugarspoon.design_system.overlay.Modal
import com.sugarspoon.design_system.overlay.rememberOverlayState
import com.sugarspoon.design_system.text.SugarText

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val modalState = rememberOverlayState()
    LaunchedEffect(key1 = true) {
        viewModel.getCredential()
    }
    Column(modifier = Modifier.fillMaxSize()) {
        SugarText(
            text = "Ações",
            modifier = Modifier
                .fillMaxWidth()
                .inlineSpacingMedium(),
            textAlign = TextAlign.Left,
            style = MaterialTheme.typography.titleMedium
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .semantics {
                    contentDescription = "de ${state.founds.size} ações"
                },
            userScrollEnabled = true,
            contentPadding = PaddingValues(horizontal = 0.dp, vertical = 2.dp),
        ) {
            items(
                count = state.founds.size,
                key = {
                    state.founds[it]?.code ?: 0
                },
                itemContent = { index ->
                    SugarLists.Founds(
                        code =  state.founds[index]?.code .orEmpty(),
                        cnpj =  state.founds[index]?.cnpj .orEmpty(),
                        fantasyName = "${state.founds[index]?.fantasyName.orEmpty()}" +
                                "\n${state.founds[index]?.type .orEmpty()}"
                        ,
                        modifier = Modifier.topSpacing(),
                        onClick = viewModel::fetchFoundsDetail
                    )
                },
            )
        }
        modalState.handleVisibility(state.openModal)

        Modal(state = modalState) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
            ) {
                SugarText(
                    text = state.fundsDetailResponse?.descricao ?: "Falha na request",
                    modifier = Modifier
                        .fillMaxWidth()
                        .inlineSpacingMedium(),
                    textAlign = TextAlign.Left,
                    style = MaterialTheme.typography.titleMedium
                )
                SugarText(
                    text = state.error,
                    modifier = Modifier
                        .fillMaxWidth()
                        .inlineSpacingMedium(),
                    textAlign = TextAlign.Left,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}