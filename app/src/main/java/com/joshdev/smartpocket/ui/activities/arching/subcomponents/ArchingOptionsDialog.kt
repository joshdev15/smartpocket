package com.joshdev.smartpocket.ui.activities.arching.subcomponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.joshdev.smartpocket.ui.activities.arching.ArchingViewModel
import com.joshdev.smartpocket.ui.components.AppText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchingOptionsDialog(viewModel: ArchingViewModel) {
    val sheetState = rememberModalBottomSheetState()
    val onClose = { viewModel.toggleArchingOptionsDialog(null, false) }

    viewModel.selectedArching.value?.let {
        if (viewModel.showArchingOptionsDialog.value) {
            ModalBottomSheet(
                onDismissRequest = { onClose() },
                sheetState = sheetState
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    AppText(
                        text = it.name,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(onClick = {
                                    viewModel.deleteArching()
                                    onClose()
                                })
                        ) {
                            AppText(
                                text = "Eliminar",
                                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                                color = Color.Red,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}