package com.joshdev.smartpocket.ui.modules.arching.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joshdev.smartpocket.R
import com.joshdev.smartpocket.domain.arching.ArcRecordItem
import com.joshdev.smartpocket.ui.components.AppText
import com.joshdev.smartpocket.ui.models.ItemProduct
import com.joshdev.smartpocket.ui.modules.arching.activity.ArchingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewItemDialog(viewModel: ArchingViewModel, recordId: Long) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    if (viewModel.showNewItem.value) {
        val availableProducts = viewModel.products.value
        val itemProducts: List<ItemProduct> = remember(availableProducts) {
            availableProducts.map { product ->
                ItemProduct(arcProduct = product)
            }
        }

        val localSelectedProducts = remember(itemProducts) {
            derivedStateOf { itemProducts.any { it.isSelected.value } }
        }

        ModalBottomSheet(
            onDismissRequest = { viewModel.toggleNewItemDialog(false) },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.inverseOnSurface,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                AppText("Agregar elemento al registro", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn {
                    items(itemProducts) { itemProduct ->
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(bottom = 10.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(20.dp))
                                .clickable(onClick = {
                                    itemProduct.isSelected.value = !itemProduct.isSelected.value
                                })
                                .background(MaterialTheme.colorScheme.surfaceBright)
                                .height(50.dp)
                                .padding(10.dp)
                        ) {
                            AppText(itemProduct.arcProduct.name)

                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (itemProduct.isSelected.value) {
                                    QtyItemProduct(
                                        value = itemProduct.quantity.value.toString(),
                                        onLeft = {
                                            if (itemProduct.quantity.value > 0) {
                                                itemProduct.quantity.value -= 1
                                            }
                                        },
                                        onRight = { itemProduct.quantity.value += 1 },
                                        onUpdate = { value -> itemProduct.quantity.value = value },
                                    )
                                }

                                Icon(
                                    painter = painterResource(if (itemProduct.isSelected.value) R.drawable.check_on else R.drawable.check_off),
                                    contentDescription = "check",
                                    tint = if (itemProduct.isSelected.value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }

                    item {
                        Button(
                            enabled = localSelectedProducts.value,
                            onClick = {
                                val productList = itemProducts.filter { it.isSelected.value }
                                    .map {
                                        ArcRecordItem(
                                            recordId = recordId,
                                            productId = it.arcProduct.id,
                                            quantity = it.quantity.value
                                        )
                                    }
                                viewModel.addAllItems(productList, recordId)
                                viewModel.toggleNewItemDialog(false)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Guardar")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun QtyItemProduct(
    value: String,
    onLeft: () -> Unit,
    onRight: () -> Unit,
    onUpdate: (Int) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(end = 20.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .width(20.dp)
                .height(30.dp)
                .clickable(onClick = { onLeft() })
                .clip(RoundedCornerShape(5.dp))
                .background(MaterialTheme.colorScheme.tertiary)
        ) {
            Icon(
                painter = painterResource(R.drawable.chevron_left),
                contentDescription = "arrow left",
                tint = MaterialTheme.colorScheme.background,
            )
        }

        BasicTextField(
            value = value,
            onValueChange = { onUpdate(it.toInt()) },
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            ),
            singleLine = true,
            maxLines = 1,
            modifier = Modifier
                .width(60.dp)
                .height(25.dp)
                .padding(horizontal = 10.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f))
                .padding(horizontal = 5.dp)
                .align(Alignment.CenterVertically),
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .width(20.dp)
                .height(30.dp)
                .clickable(onClick = { onRight() })
                .clip(RoundedCornerShape(5.dp))
                .background(MaterialTheme.colorScheme.tertiary)
        ) {
            Icon(
                painter = painterResource(R.drawable.chevron_right),
                contentDescription = "arrow left",
                tint = MaterialTheme.colorScheme.background,
            )
        }
    }
}