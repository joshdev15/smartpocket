package com.joshdev.smartpocket.ui.modules.arching.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joshdev.smartpocket.ui.components.AppText
import com.joshdev.smartpocket.ui.modules.arching.activity.ArchingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewRecordDialog(viewModel: ArchingViewModel, archingId: Long) {
    val sheetState = rememberModalBottomSheetState()

    if (viewModel.showNewRecordDialog.value) {
        var recordName by remember { mutableStateOf("") }

        ModalBottomSheet(
            onDismissRequest = { viewModel.toggleNewRecordDialog(false) },
            sheetState = sheetState
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                AppText("Nuevo Registro")
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = recordName,
                    onValueChange = { recordName = it },
                    label = { Text("Nombre del registro") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        if (recordName.isNotBlank()) {
                            viewModel.addRecord(archingId, recordName)
                        } else {
                            viewModel.addRecord(archingId, null)
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