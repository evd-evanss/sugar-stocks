package com.sugarspoon.pocketstocks.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.sugarspoon.design_system.bottombar.BottomBar
import com.sugarspoon.design_system.buttons.MenuButton
import com.sugarspoon.design_system.icons.SugarSpoonIcons
import com.sugarspoon.design_system.overlay.SugarDialog
import com.sugarspoon.design_system.theme.DesignSystemTheme
import com.sugarspoon.pocketstocks.di.ViewModelInjector
import com.sugarspoon.pocketstocks.navigation.Screens
import com.sugarspoon.pocketstocks.navigation.navigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelInjector: ViewModelInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val tabSelected = remember { mutableStateOf("") }
            LaunchedEffect(true) {
                tabSelected.value = "${Screens.Home.title}, item 1 de 3"
                viewModelInjector.homeViewModel.onRefresh()
            }
            val isVisible = remember {
                mutableStateOf(false)
            }
            val homeUiState = viewModelInjector.homeViewModel.uiState.invoke()
            DesignSystemTheme(darkTheme = true) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)) {
                    NavHost(
                        navController = navController,
                        startDestination = Screens.Home.name
                    ) {
                        navigation(
                            viewModels = viewModelInjector,
                            homeUiState = homeUiState,
                            onFinish = { finish() },
                        )
                    }
                    BottomBar(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                    ) {
                        MenuButton(
                            modifier = Modifier,
                            icon = SugarSpoonIcons.Outline.Home,
                            tabName = "${Screens.Home.title}, item 1 de 3",
                            isSelected = tabSelected.value == "${Screens.Home.title}, item 1 de 3",
                        ) {
                            tabSelected.value = it
                            navController.navigate(Screens.Home.name) {
                                popUpTo(navController.graph.id) {
                                    inclusive = true
                                }
                            }
                        }
                        MenuButton(
                            modifier = Modifier,
                            icon = SugarSpoonIcons.Outline.Note,
                            tabName = "${Screens.Wallet.title}, item 2 de 3",
                            isSelected = tabSelected.value == "${Screens.Wallet.title}, item 2 de 3",
                        ) {
                            tabSelected.value = it
                            navController.navigate(Screens.Wallet.name) {
                                popUpTo(navController.graph.id) {
                                    inclusive = true
                                }
                            }
                        }
                        MenuButton(
                            modifier = Modifier,
                            icon = SugarSpoonIcons.Outline.Presentation1,
                            tabName = "${Screens.Analysis.title}, item 3 de 3",
                            isSelected = tabSelected.value == "${Screens.Analysis.title}, item 3 de 3"
                        ) {
                            tabSelected.value = it
                            navController.navigate(Screens.Analysis.name) {
                                popUpTo(navController.graph.id) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                    SugarDialog(
                        isVisible = isVisible.value,
                        title = "SAIR",
                        message = "Você realmente deseja sair do App?",
                        onClickRight = {
                            finish()
                        }
                    ) {
                        isVisible.value = false
                    }
                }
                BackHandler(true) {
                    isVisible.value = isVisible.value.not()
                }
            }
        }
    }
}