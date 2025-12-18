package com.joshdev.smartpocket.ui.modules.ledger.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joshdev.smartpocket.domain.ledger.LedTransaction
import com.joshdev.smartpocket.ui.components.AppSwitch
import com.joshdev.smartpocket.ui.components.AppText
import com.joshdev.smartpocket.ui.modules.ledger.activity.LedgerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTransactionDialog(ledgerId: Long, viewModel: LedgerViewModel) {
    val sheetState = rememberModalBottomSheetState()
    var txName by remember { mutableStateOf("") }
    var txType by remember { mutableStateOf(false) }
    var txAmount by remember { mutableStateOf("") }

    val onClose = {
        viewModel.toggleNewTransactionDialog(false)
    }

    if (viewModel.showNewTransactionDialog.value) {
        ModalBottomSheet(
            onDismissRequest = { onClose() },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                AppText(
                    text = "Nueva Transacción",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize
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
                            val tx = LedTransaction(
                                name = txName,
                                type = if (txType) LedTransaction.TxType.INCOME else LedTransaction.TxType.EGRESS,
                                amount = txAmount.toDouble(),
                                date = System.currentTimeMillis(),
                                description = "",
                                ledgerId = ledgerId,
                                currencyId = 0,
                                postBalance = viewModel.ledger.value?.totalBalance?.minus(txAmount.toDouble())
                                    ?: 0.0,
                                hasProducts = false,
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