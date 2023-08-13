package com.sugarspoon.pocketstocks.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.sugarspoon.design_system.theme.DesignSystemTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : ComponentActivity() {

    private val viewModel: DetailsViewModel by viewModels()

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
                    uiState = state,
                    code = code,
                    logo = logo,
                )
            }
        }
    }

    companion object {

        private const val CODE = "code"
        private const val LOGO = "logo"

        fun Context.getDetailsActivityIntent(code: String, logo: String) : Intent {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra(CODE, code)
            intent.putExtra(LOGO, logo)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            return intent
        }
    }
}