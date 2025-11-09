package com.joshdev.smartpocket.ui.activities.invoiceList.subcomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joshdev.smartpocket.ui.activities.invoiceList.TransactionListViewModel
import com.joshdev.smartpocket.ui.activities.ledger.subcomponents.LedgerResume
import com.joshdev.smartpocket.ui.components.TransactionCard

@Composable
fun TransactionScreen(
    innerPadding: PaddingValues,
    viewModel: TransactionListViewModel,
    ledgerId: String,
) {
    val transactions = viewModel.transactions.value
    val filteredTransactions = transactions.filter { it.ledgerId == ledgerId }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.inverseOnSurface)
            .padding(innerPadding)
            .padding(horizontal = 10.dp)
    ) {
        LazyColumn {
            item {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(15.dp)
                )
            }

            item {
                viewModel.ledger.value?.let {
                    LedgerResume(it, filteredTransactions)
                }
            }

            itemsIndexed(filteredTransactions) { idx, it ->
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(if (idx == 0) 15.dp else 0.dp)
                )

                TransactionCard(
                    tx = it,
                    onClick = { viewModel.goToTransaction(it.id) },
                    onLongClick = { tx -> viewModel.toggleTransactionOptionsDialog(tx, true) }
                )
            }

            item {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(75.dp)
                )
            }
        }
    }

    NewTransactionDialog(ledgerId, viewModel)

    TransactionOptionsDialog(viewModel)
}