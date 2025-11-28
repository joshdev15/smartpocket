package com.joshdev.smartpocket.ui.modules.ledger.components

import androidx.compose.foundation.background
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
import com.joshdev.smartpocket.ui.components.AppTopBarBasic
import com.joshdev.smartpocket.ui.components.FloatingButton
import com.joshdev.smartpocket.ui.components.TransactionCard
import com.joshdev.smartpocket.ui.modules.ledger.activity.LedgerViewModel
import com.joshdev.smartpocket.ui.utils.UiUtils.SCREEN_FLOATING_PADDING
import com.joshdev.smartpocket.ui.utils.UiUtils.SCREEN_PADDING

@Composable
fun TransactionScreen(
    viewModel: LedgerViewModel,
    ledgerId: String
) {
    val transactions = viewModel.transactions.value
    val filteredTransactions = transactions.filter { it.ledgerId == ledgerId }

    Scaffold(
        topBar = { AppTopBarBasic("Transacciones de ${viewModel.ledger.value?.name} - $ledgerId") },
        floatingActionButton = {
            FloatingButton("Nueva Transacción") {
                viewModel.toggleNewTransactionDialog(true)
            }
        },
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.inverseOnSurface)
                    .padding(innerPadding)
                    .padding(horizontal = SCREEN_PADDING)
                    .padding(top = SCREEN_PADDING, bottom = SCREEN_FLOATING_PADDING)
            ) {
                item {
                    viewModel.ledger.value?.let {
                        LedgerResume(it, filteredTransactions)
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(SCREEN_PADDING)
                        )
                    }
                }

                itemsIndexed(filteredTransactions) { idx, it ->
                    TransactionCard(
                        tx = it,
                        onClick = { viewModel.goToTransaction(it.id) },
                        onLongClick = { tx ->
                            viewModel.toggleTransactionOptionsDialog(
                                tx,
                                true
                            )
                        }
                    )
                }
            }

            NewTransactionDialog(ledgerId, viewModel)

            TransactionOptionsDialog(viewModel)
        }
    )
}