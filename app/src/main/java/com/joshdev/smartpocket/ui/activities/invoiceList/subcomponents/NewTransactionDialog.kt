package com.joshdev.smartpocket.ui.activities.invoiceList.subcomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.joshdev.smartpocket.domain.models.LedgerTransaction
import com.joshdev.smartpocket.ui.activities.invoiceList.TransactionListViewModel
import com.joshdev.smartpocket.ui.components.AppSwitch
import com.joshdev.smartpocket.ui.components.AppText

@Composable
fun NewTransactionDialog(ledgerId: String, viewModel: TransactionListViewModel) {
    var txName by remember { mutableStateOf("") }
    var txType by remember { mutableStateOf(false) }
    var txAmount by remember { mutableStateOf("") }

    val onClose = {
        viewModel.toggleNewTransactionDialog(false)
    }

    if (viewModel.showNewTransactionDialog.value) {
        Dialog(
            onDismissRequest = { onClose() },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
            )
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceBright)
                    .padding(20.dp)
            ) {
                AppText(
                    text = "Nueva Transacción",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize
                )

                OutlinedTextField(
                    value = txName,
                    onValueChange = {
                        if (txName.length <= 20) {
                            txName = it
                        }
                    },
                    label = { Text("Nombre") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                )

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                ) {
                    AppSwitch(
                        leftOptionText = "Egreso",
                        rightOptionText = "Ingreso",
                        isChecked = txType,
                        onChange = { txType = !txType },
                        checkedTextColor = MaterialTheme.colorScheme.onBackground,
                        enableColor = MaterialTheme.colorScheme.primaryContainer,
                    )
                }

                OutlinedTextField(
                    value = txAmount,
                    onValueChange = { txAmount = it },
                    label = { Text("Monto") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                )

                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            val tx = LedgerTransaction(
                                id = "",
                                name = txName,
                                type = if (txType) LedgerTransaction.TxType.INCOME else LedgerTransaction.TxType.EGRESS,
                                amount = txAmount.toDouble(),
                                date = System.currentTimeMillis(),
                                description = "",
                                ledgerId = ledgerId,
                                currencyId = 0,
                                postBalance = viewModel.ledger.value?.totalBalance?.minus(txAmount.toDouble())
                                    ?: 0.0,
                                hasProducts = false,
                                ledgerProducts = emptyList()
                            )

                            viewModel.addTransaction(tx)
                            txName = ""
                            txAmount = ""
                            onClose()
                        },
                        modifier = Modifier
                            .width(100.dp)
                            .padding(top = 10.dp)
                    ) {
                        AppText("Crear")
                    }
                }
            }
        }
    }
}