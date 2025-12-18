package com.joshdev.smartpocket.ui.modules.ledger.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joshdev.smartpocket.ui.components.AppTopBarBasic
import com.joshdev.smartpocket.ui.components.FastPanel
import com.joshdev.smartpocket.ui.components.FloatingButton
import com.joshdev.smartpocket.ui.components.LedgerCard
import com.joshdev.smartpocket.ui.models.FastPanelOption
import com.joshdev.smartpocket.ui.modules.ledger.activity.LedgerViewModel
import com.joshdev.smartpocket.ui.modules.ledger.components.LedgerOptionsDialog
import com.joshdev.smartpocket.ui.modules.ledger.components.NewLedgerDialog
import com.joshdev.smartpocket.ui.utils.UiUtils.SCREEN_FLOATING_PADDING
import com.joshdev.smartpocket.ui.utils.UiUtils.SCREEN_PADDING

@Composable
fun LedgerScreen(viewModel: LedgerViewModel) {
    val options = listOf(
        FastPanelOption(
            id = FastPanelOption.IDs.CATEGORIES_LEDGER,
            name = "Categorías de Cuentas",
        ),
        FastPanelOption(
            id = FastPanelOption.IDs.CURRENCIES,
            name = "Divisas",
        ),
    )

    Scaffold(
        topBar = {
            AppTopBarBasic("Cuentas")
        },
        floatingActionButton = {
            FloatingButton("Nueva Cuenta") {
                viewModel.toggleNewRecordDialog(true)
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.inverseOnSurface)
                    .padding(innerPadding)
            ) {
                FastPanel(options) {
                    viewModel.goTo(it)
                }

                LazyColumn(
                    modifier = Modifier.padding(horizontal = SCREEN_PADDING)
                ) {
                    itemsIndexed(viewModel.ledgers.value) { idx, it ->
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(if (idx == 0) SCREEN_PADDING else 0.dp)
                        )

                        LedgerCard(
                            it,
                            onClick = {
                                it.id?.let { ledgerId ->
                                    viewModel.navToTransactions(ledgerId)
                                }
                            },
                            onLongClick = { viewModel.toggleLedgerOptionsDialog(it, true) }
                        )
                    }

                    item {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(SCREEN_FLOATING_PADDING)
                        )
                    }
                }
            }

            NewLedgerDialog(viewModel)

            LedgerOptionsDialog(viewModel)
        }
    )
}