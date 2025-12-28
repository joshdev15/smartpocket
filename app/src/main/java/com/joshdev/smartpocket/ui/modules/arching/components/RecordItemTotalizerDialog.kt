package com.joshdev.smartpocket.ui.modules.arching.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.joshdev.smartpocket.R
import com.joshdev.smartpocket.ui.components.AppText
import com.joshdev.smartpocket.ui.modules.arching.activity.ArchingViewModel
import com.joshdev.smartpocket.ui.utils.UiUtils.formatAmount

@Composable
fun RecordItemTotalizerDialog(viewModel: ArchingViewModel) {
    if (viewModel.showItemTotalizer.value) {
        val itemList = viewModel.recordItems.value
        val arcProductList = viewModel.products.value
        val currencyList = viewModel.currencies.value
        val totalList = viewModel.itemTotals.value

        val subTotals = itemList.map { recItem ->
            val currentProduct = arcProductList.find { recItem.productId == it.id }
            currentProduct?.let {
                recItem.quantity.toDouble() * currentProduct.price
            } ?: 0.0
        }

        val totalQuantities = itemList.sumOf { it.quantity }

        val total = subTotals.sum()

        Dialog(
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
            ),
            onDismissRequest = { viewModel.toggleItemTotalizer(false) }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.surfaceBright)
                    .padding(10.dp)
            ) {
                val green = colorResource(id = R.color.income)
                val red = colorResource(id = R.color.egress)

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AppText("Totales")
                }

                Spacer(
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f))
                )

                LazyColumn(
                    modifier = Modifier.wrapContentSize()
                ) {
                    item {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            AppText("Cant. Productos", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            AppText(totalQuantities.toString(), fontSize = 12.sp)
                        }
                    }

                    item {
                        Spacer(
                            modifier = Modifier
                                .padding(vertical = 5.dp)
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f))
                        )
                    }

                    items(totalList) { total ->
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            val description = "${total.categoryName} (${total.productName} x ${total.itemQuantity})"
                            val amount = total.itemQuantity * total.productPrice

                            AppText(description, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            AppText(
                                formatAmount(amount),
                                color = MaterialTheme.colorScheme.onBackground,
                                fontSize = 12.sp
                            )
                        }
                    }

                    item {
                        Spacer(
                            modifier = Modifier
                                .padding(vertical = 5.dp)
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f))
                        )
                    }

                    items(currencyList) { currency ->
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            AppText(currency.name, fontSize = 12.sp, fontWeight = FontWeight.Bold)

                            Row {
                                AppText(
                                    currency.symbol,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.tertiary,
                                    modifier = Modifier.padding(end = 5.dp)
                                )

                                AppText(
                                    formatAmount(total * currency.rate),
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}