package com.joshdev.smartpocket.ui.modules.arching.components

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
import com.joshdev.smartpocket.domain.models.Arching
import com.joshdev.smartpocket.ui.modules.arching.activity.ArchingViewModel
import com.joshdev.smartpocket.ui.components.AppText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewArchingDialog(viewModel: ArchingViewModel) {
    val sheetState = rememberModalBottomSheetState()
    var archingName by remember { mutableStateOf("") }

    val onClose = {
        archingName = ""
        viewModel.toggleNewArchingDialog(false)
    }

    if (viewModel.showNewArchingDialog.value) {
        ModalBottomSheet(
            onDismissRequest = { onClose() },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                AppText(
                    text = "Nuevo Cierre",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize
                )

                OutlinedTextField(
                    value = archingName,
                    onValueChange = { archingName = it },
                    label = { Text("Nombre del cierre") },
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
                        enabled = archingName != "",
                        onClick = {
                            val newArching = Arching(
                                name = archingName,
                                creationDate = System.currentTimeMillis(),
                            )

                            viewModel.addArching(newArching)
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