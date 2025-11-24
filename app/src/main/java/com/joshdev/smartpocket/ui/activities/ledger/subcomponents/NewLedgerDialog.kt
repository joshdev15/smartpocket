package com.joshdev.smartpocket.ui.activities.ledger.subcomponents

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
import com.joshdev.smartpocket.domain.models.Ledger
import com.joshdev.smartpocket.ui.activities.ledger.LedgerViewModel
import com.joshdev.smartpocket.ui.components.AppText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewLedgerDialog(viewModel: LedgerViewModel) {
    val sheetState = rememberModalBottomSheetState()
    var ledgerName by remember { mutableStateOf("") }
    var ledgerCapital by remember { mutableStateOf("") }

    val onClose = {
        ledgerCapital = ""
        ledgerName = ""
        viewModel.toggleNewRecordDialog(false)
    }

    if (viewModel.showNewLedgerDialog.value) {
        ModalBottomSheet(
            onDismissRequest = { onClose() },
            sheetState = sheetState,
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                AppText(
                    text = "Nueva Cuenta",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize
                )

                OutlinedTextField(
                    value = ledgerName,
                    onValueChange = { ledgerName = it },
                    label = { Text("Nombre de la cuenta") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                )

                OutlinedTextField(
                    value = ledgerCapital,
                    onValueChange = { ledgerCapital = it },
                    label = { Text("Capital inicial") },
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
                        enabled = ledgerName != "",
                        onClick = {
                            if (ledgerCapital.isEmpty()) ledgerCapital = "0"

                            val newLedger = Ledger(
                                name = ledgerName,
                                initialCapital = ledgerCapital.toDouble(),
                                totalBalance = ledgerCapital.toDouble(),
                                creationDate = System.currentTimeMillis(),
                            )

                            viewModel.addLedger(newLedger)
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