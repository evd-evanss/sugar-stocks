package com.sugarspoon.pocketfinance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.sugarspoon.design_system.Dimensions.inlineSpacingSmall
import com.sugarspoon.design_system.Dimensions.stackSpacingMedium
import com.sugarspoon.design_system.SugarTextField
import com.sugarspoon.design_system.bottombar.BottomBar
import com.sugarspoon.design_system.buttons.ButtonIcon
import com.sugarspoon.design_system.theme.DesignSystemTheme
import com.sugarspoon.design_system.topbar.TopBar
import com.sugarspoon.icons.generated.SugarSpoonIcons
import com.sugarspoon.pocketfinance.anim.CircularReveal
import com.sugarspoon.pocketfinance.navigation.Screens
import com.sugarspoon.pocketfinance.navigation.homeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val tabSelected = remember { mutableStateOf(Screens.Home.name) }
            val isDarkTheme = remember { mutableStateOf(true) }

            CircularReveal(
                targetState = isDarkTheme.value,
                animationSpec = tween(2000)
            ) { localTheme ->
                DesignSystemTheme(darkTheme = localTheme) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = {
                            BottomBar(
                                modifier = Modifier
                                    .stackSpacingMedium()
                                    .inlineSpacingSmall(),
                                onSelectTab = {
                                    tabSelected.value = it
                                }
                            ) {
                                ButtonIcon(
                                    modifier = Modifier,
                                    icon = SugarSpoonIcons.Outline.Home,
                                    tabName = Screens.Home.name,
                                    selected = tabSelected.value == Screens.Home.name
                                ) {
                                    tabSelected.value = it
                                    navController.navigate(Screens.Home.name)
                                }
                                ButtonIcon(
                                    modifier = Modifier,
                                    icon = SugarSpoonIcons.Outline.Presentation1,
                                    tabName = Screens.Analysis.name,
                                    selected = tabSelected.value == Screens.Analysis.name
                                ) {
                                    tabSelected.value = it
                                    navController.navigate(Screens.Analysis.name)
                                }
                                ButtonIcon(
                                    modifier = Modifier,
                                    icon = SugarSpoonIcons.Outline.Wallet,
                                    tabName = Screens.Wallet.name,
                                    selected = tabSelected.value == Screens.Wallet.name
                                ) {
                                    tabSelected.value = it
                                    navController.navigate(Screens.Wallet.name)
                                }
                                ButtonIcon(
                                    modifier = Modifier,
                                    icon = SugarSpoonIcons.Outline.User,
                                    tabName = Screens.Profile.name,
                                    selected = tabSelected.value == Screens.Profile.name
                                ) {
                                    tabSelected.value = it
                                    navController.navigate(Screens.Profile.name)
                                }
                            }
                        },
                        topBar = {
                            TopBar(
                                modifier = Modifier,
                                title = tabSelected.value,
                                rightDescription = "Notificações",
                                iconRight = if(localTheme) SugarSpoonIcons.Outline.Sun else SugarSpoonIcons.Outline.Moon,
                                onClickRight = {
                                    isDarkTheme.value = !isDarkTheme.value
                                },
                            )
                        },
                        contentWindowInsets = WindowInsets.ime,
                        content = { paddingValues ->
                            paddingValues.apply {
                                Box(
                                    modifier = Modifier.fillMaxSize().padding(this)
                                ) {
                                    NavHost(
                                        navController = navController,
                                        startDestination = Screens.Home.name
                                    ) {
                                        homeScreen()
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TextPreview() {
    SugarTextField(
        value = "Oi",
        hint = "Digite",
        modifier = Modifier
    ) {

    }
}