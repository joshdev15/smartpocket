package com.joshdev.smartpocket.ui.activities.ledger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Scaffold
import com.joshdev.smartpocket.ui.activities.ledger.subcomponents.LedgerScreen
import com.joshdev.smartpocket.ui.components.AppTopBarBasic
import com.joshdev.smartpocket.ui.components.FloatingButton
import com.joshdev.smartpocket.ui.theme.SmartPocketTheme

class LedgerActivity : ComponentActivity() {
    private val viewModel by viewModels<LedgerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        viewModel.start(this, this@LedgerActivity)

        setContent {
            SmartPocketTheme {
                Scaffold(
                    topBar = {
                        AppTopBarBasic("Cuentas")
                    },
                    floatingActionButton = {
                        FloatingButton("Nueva Cuenta") {
                            viewModel.toggleNewRecordDialog(
                                true
                            )
                        }
                    },
                    content = { innerPadding ->
                        LedgerScreen(innerPadding, viewModel)
                    }
                )
            }
        }
    }
}