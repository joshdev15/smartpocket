package com.joshdev.smartpocket.ui.activities.currency

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Scaffold
import com.joshdev.smartpocket.ui.activities.currency.subcomponents.CurrencyScreen
import com.joshdev.smartpocket.ui.components.AppTopBarBasic
import com.joshdev.smartpocket.ui.components.FloatingButton
import com.joshdev.smartpocket.ui.theme.SmartPocketTheme

class CurrencyActivity : ComponentActivity() {
    val viewModel by viewModels<CurrencyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.start(this, this@CurrencyActivity)

        setContent {
            SmartPocketTheme {
                Scaffold(
                    topBar = { AppTopBarBasic("Divisas") },
                    floatingActionButton = {
                        FloatingButton("Nueva Divisa") {
                            viewModel.toggleNewCurrencyDialog(true)
                        }
                    },
                    content = { innerPadding -> CurrencyScreen(innerPadding, viewModel) }
                )
            }
        }
    }
}