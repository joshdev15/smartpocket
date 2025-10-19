package com.joshdev.smartpocket.ui.activities.invoiceList

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Scaffold
import com.joshdev.smartpocket.ui.activities.invoiceList.subcomponents.TransactionScreen
import com.joshdev.smartpocket.ui.components.AppTopBarBasic
import com.joshdev.smartpocket.ui.components.FloatingButton
import com.joshdev.smartpocket.ui.theme.SmartPocketTheme

class TransactionListActivity : ComponentActivity() {
    private val viewModel by viewModels<TransactionListViewModel>()
    private var ledgerId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        intent.getStringExtra("ledgerId")?.let { ledgerId = it }

        viewModel.start(this, this@TransactionListActivity, ledgerId)

        setContent {
            SmartPocketTheme {
                Scaffold(
                    topBar = { AppTopBarBasic("Transacciones de ${viewModel.ledger.value?.name}") },
                    floatingActionButton = {
                        FloatingButton("Nueva Transacción") {
                            viewModel.toggleNewTransactionDialog(
                                true
                            )
                        }
                    },
                    content = { innerPadding ->
                        TransactionScreen(
                            innerPadding,
                            viewModel,
                            ledgerId,
                        )
                    }
                )
            }
        }
    }
}