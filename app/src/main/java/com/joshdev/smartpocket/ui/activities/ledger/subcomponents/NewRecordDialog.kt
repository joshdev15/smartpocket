package com.joshdev.smartpocket.ui.activities.ledger.subcomponents

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
import com.joshdev.smartpocket.domain.models.Ledger
import com.joshdev.smartpocket.ui.activities.ledger.LedgerViewModel
import com.joshdev.smartpocket.ui.components.AppText
import java.util.Calendar

@Composable
fun NewRecordDialog(viewModel: LedgerViewModel) {
    var recName by remember { mutableStateOf("") }

    val onClose = {
        viewModel.toggleNewRecordDialog(false)
    }

    if (viewModel.showNewRecordDialog.value) {
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
                    text = "Nuevo Registro",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize
                )

                OutlinedTextField(
                    value = recName,
                    onValueChange = { recName = it },
                    label = { Text("Nombre del Registro") },
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
                            val inv = Ledger(
                                name = recName,
                                author = "",
                                year = Calendar.YEAR,
                                month = Calendar.MONTH,
                                creationDate = System.currentTimeMillis(),
                            )

                            viewModel.addLedger(inv)
                            recName = ""
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