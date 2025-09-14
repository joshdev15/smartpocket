package com.joshdev.smartpocket.ui.activities.currency

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import com.joshdev.smartpocket.domain.models.Currency
import com.joshdev.smartpocket.ui.activities.currency.subcomponents.CurrencyScreen
import com.joshdev.smartpocket.ui.components.AppTopBarBasic
import com.joshdev.smartpocket.ui.theme.SmartPocketTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrencyActivity : ComponentActivity() {
    val viewModel by viewModels<CurrencyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.start(this, this@CurrencyActivity)

        setContent {
            SmartPocketTheme {
                Scaffold(
                    topBar = { AppTopBarBasic("Divisas") },
                    content = { innerPadding -> CurrencyScreen(innerPadding, viewModel) }
                )
            }
        }
    }
}