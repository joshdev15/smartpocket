package com.joshdev.smartpocket.ui.modules.arching.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joshdev.smartpocket.ui.components.AppText
import com.joshdev.smartpocket.ui.modules.arching.activity.ArchingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchingRecordOptionsDialog(viewModel: ArchingViewModel) {
    val sheetState = rememberModalBottomSheetState()

    val onClose = {
        viewModel.toggleArchingRecordOptionsDialog(null, false)
    }

    if (viewModel.showArchingRecordOptionsDialog.value) {
        ModalBottomSheet(
            onDismissRequest = { onClose() },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.inverseOnSurface
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                AppText(
                    text = "Opciones de registro",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize
                )

                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            viewModel.deleteArchingRecord()
                            onClose()
                        },
                        modifier = Modifier.padding(top = 10.dp, end = 8.dp)
                    ) {
                        AppText("Eliminar")
                    }
                }
            }
        }
    }
}