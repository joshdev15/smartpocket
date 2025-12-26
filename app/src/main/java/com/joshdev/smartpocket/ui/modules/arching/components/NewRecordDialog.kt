package com.joshdev.smartpocket.ui.modules.arching.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.joshdev.smartpocket.ui.components.AppSwitch
import com.joshdev.smartpocket.ui.components.AppText
import com.joshdev.smartpocket.ui.modules.arching.activity.ArchingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewRecordDialog(viewModel: ArchingViewModel, archingId: Long) {
    val sheetState = rememberModalBottomSheetState()

    if (viewModel.showNewRecordDialog.value) {
        var recordName by remember { mutableStateOf("") }
        var recordAmount by remember { mutableStateOf("") }
        var isDeduction by remember { mutableStateOf(false) }

        ModalBottomSheet(
            onDismissRequest = { viewModel.toggleNewRecordDialog(false) },
            sheetState = sheetState
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                AppText("Nuevo Registro")
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AppSwitch(
                        leftOptionText = "Jornada",
                        rightOptionText = "Deducción",
                        isChecked = isDeduction,
                        onChange = { isDeduction = !isDeduction },
                        checkedTextColor = MaterialTheme.colorScheme.onBackground,
                        enableColor = MaterialTheme.colorScheme.primaryContainer,
                        disableColor = MaterialTheme.colorScheme.surfaceBright,
                    )
                }

                OutlinedTextField(
                    value = recordName,
                    onValueChange = { recordName = it },
                    label = { Text("Nombre del registro") },
                    modifier = Modifier.fillMaxWidth()
                )

                if (isDeduction) {
                    OutlinedTextField(
                        value = recordAmount,
                        onValueChange = { recordAmount = it },
                        label = { Text("Monto de deducción") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        val amount = recordAmount.toDoubleOrNull() ?: 0.0

                        if (recordName.isNotBlank()) {
                            viewModel.addRecord(archingId, recordName, isDeduction, amount)
                        } else {
                            viewModel.addRecord(archingId, null, isDeduction, amount)
                        }

                        viewModel.toggleNewRecordDialog(false)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Guardar")
                }
            }
        }
    }
}