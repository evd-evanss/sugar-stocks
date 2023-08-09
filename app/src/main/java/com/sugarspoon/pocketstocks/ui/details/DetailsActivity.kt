package com.sugarspoon.pocketstocks.ui.details

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.sugarspoon.design_system.theme.DesignSystemTheme
import com.sugarspoon.pocketstocks.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val code = intent.getStringExtra("code").orEmpty()
        val logo = intent.getStringExtra("logo").orEmpty()
        setContent {
            val state by viewModel.state.collectAsState()
            DesignSystemTheme(true) {
                StockDetailsScreen(
                    viewModel = viewModel,
                    onBackPressed = ::finish,
                    state = state,
                    code = code,
                    logo = logo,
                )
            }
        }
    }
}