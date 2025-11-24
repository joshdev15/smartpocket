package com.joshdev.smartpocket.ui.micromodules.currency.components

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
import com.joshdev.smartpocket.ui.micromodules.currency.activity.CurrencyViewModel
import com.joshdev.smartpocket.ui.components.AppText
import com.joshdev.smartpocket.ui.utils.UiUtils.ellipsis

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyOptionsDialog(viewModel: CurrencyViewModel) {
    val sheetState = rememberModalBottomSheetState()
    val onClose = { viewModel.toggleCurrencyOptionsDialog(null) }

    viewModel.selectedCurrency.value?.let {
        if (viewModel.showCurrencyOptionsDialog.value) {
            ModalBottomSheet(
                onDismissRequest = { onClose() },
                sheetState = sheetState,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        AppText(
                            text = "Opciones",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = MaterialTheme.typography.titleLarge.fontSize
                        )

                        AppText(
                            text = ellipsis(it.name, 15),
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = MaterialTheme.typography.bodySmall.fontSize
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                    ) {
//                        Row(
//                            horizontalArrangement = Arrangement.Center,
//                            verticalAlignment = Alignment.CenterVertically,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .clickable(onClick = {})
//                        ) {
//                            AppText(
//                                text = "Editar",
//                                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
//                                modifier = Modifier.padding(10.dp)
//                            )
//                        }

                        if (it.name != "USD") {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(onClick = {
                                        viewModel.deleteCurrency()
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
}
