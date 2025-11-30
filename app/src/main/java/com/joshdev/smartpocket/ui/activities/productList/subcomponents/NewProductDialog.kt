package com.joshdev.smartpocket.ui.activities.productList.subcomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.joshdev.smartpocket.domain.ledger.Product
import com.joshdev.smartpocket.ui.activities.productList.ProductListViewModel
import com.joshdev.smartpocket.ui.components.AppText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewProductDialog(invoiceId: String, viewModel: ProductListViewModel) {
    val sheetState = rememberModalBottomSheetState()
    var proName by remember { mutableStateOf("") }
    var proCost by remember { mutableStateOf("") }
    var proQty by remember { mutableStateOf("") }

    val onClose = { viewModel.toggleNewInvoiceDialog(false) }

    if (viewModel.showNewProductDialog.value) {
        ModalBottomSheet(
            onDismissRequest = { onClose() },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceBright)
                    .padding(20.dp)
            ) {
                AppText(
                    text = "Nueva Producto",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize
                )

                OutlinedTextField(
                    value = proName,
                    onValueChange = { proName = it },
                    label = { AppText("Nombre de Producto") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                )

                OutlinedTextField(
                    value = proCost,
                    onValueChange = { proCost = it },
                    label = { AppText("Costo Individual") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                )

                OutlinedTextField(
                    value = proQty,
                    onValueChange = { proQty = it },
                    label = { AppText("Cantidad") },
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
                            val pro = Product(
                                invoiceId = invoiceId,
                                name = proName,
                                quantity = proQty.toInt(),
                                cost = proCost.toDouble(),
                                currency = 0,
                                customRate = 0.0,
                                order = 0,
                                baseCost = 0.0,
                            )

                            viewModel.addProduct(pro)
                            proName = ""
                            proCost = ""
                            proQty = ""
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