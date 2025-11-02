package com.joshdev.smartpocket.ui.activities.ledger.subcomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.joshdev.smartpocket.ui.activities.ledger.LedgerViewModel
import com.joshdev.smartpocket.ui.components.AppText

@Composable
fun LedgerOptionsDialog(viewModel: LedgerViewModel) {
    val onClose = { viewModel.toggleLedgerOptionsDialog(null, false) }

    viewModel.selectedLedger.value?.let {
        if (viewModel.showLedgerOptionsDialog.value) {
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
                                    viewModel.deleteLedger()
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