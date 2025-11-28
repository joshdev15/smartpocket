package com.joshdev.smartpocket.ui.modules.currency.components

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
import com.joshdev.smartpocket.domain.models.Currency
import com.joshdev.smartpocket.ui.modules.currency.activity.CurrencyViewModel
import com.joshdev.smartpocket.ui.components.AppText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewCurrencyDialog(viewModel: CurrencyViewModel) {
    val sheetState = rememberModalBottomSheetState()
    var currName by remember { mutableStateOf("") }
    var currSymbol by remember { mutableStateOf("") }
    var currExRate by remember { mutableStateOf("") }

    val onClose = {
        viewModel.toggleNewCurrencyDialog(false)
    }

    if (viewModel.showNewCurrencyDialog.value) {
        ModalBottomSheet(
            onDismissRequest = { onClose() },
            sheetState = sheetState,
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

                OutlinedTextField(
                    value = currName,
                    onValueChange = {
                        if (currName.length <= 20) {
                            currName = it
                        }
                    },
                    label = { Text("Nombre") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                )

                OutlinedTextField(
                    value = currSymbol,
                    onValueChange = {
                        if (currSymbol.length <= 3) {
                            currSymbol = it
                        }
                    },
                    label = { Text("Simbolo") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                )

                OutlinedTextField(
                    value = currExRate,
                    onValueChange = { currExRate = it },
                    label = { Text("Taza de Cambio") },
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
                            val currency = Currency(
                                name = currName,
                                symbol = currSymbol,
                                rate = currExRate.toDouble(),
                            )

                            viewModel.addCurrency(currency)
                            currName = ""
                            currSymbol = ""
                            currExRate = ""
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