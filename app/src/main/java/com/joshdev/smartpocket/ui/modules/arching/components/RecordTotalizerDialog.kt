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
import com.joshdev.smartpocket.domain.arching.ArcRecord
import com.joshdev.smartpocket.ui.components.AppText
import com.joshdev.smartpocket.ui.modules.arching.activity.ArchingViewModel
import com.joshdev.smartpocket.ui.utils.UiUtils.formatAmount

@Composable
fun RecordTotalizerDialog(viewModel: ArchingViewModel) {
    if (viewModel.showRecordTotalizerDialog.value) {
        val recordList = viewModel.records.value
        val currencyList = viewModel.currencies.value
        val totalsMap = viewModel.totalsMap.value

        Dialog(
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
            ),
            onDismissRequest = { viewModel.toggleRecordTotalizerDialog(false) }
        ) {
            val workingTotal =
                recordList.filter { it.type == ArcRecord.RecType.WorkingDay }
                    .sumOf { it.totalAmount }

            val deductionTotal =
                recordList.filter { it.type == ArcRecord.RecType.Deduction }
                    .sumOf { it.totalAmount }

            val finalAmount = workingTotal - deductionTotal

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
                        if (recordList.isEmpty()) {
                            AppText(
                                "Aun no hay registros",
                                color = MaterialTheme.colorScheme.secondary,
                            )
                        }
                    }

                    items(recordList) { record ->
                        val typeColor = when (record.type) {
                            ArcRecord.RecType.WorkingDay -> green
                            ArcRecord.RecType.Deduction -> red
                        }

                        val symbol = when (record.type) {
                            ArcRecord.RecType.WorkingDay -> {
                                "-"
                            }

                            ArcRecord.RecType.Deduction -> {
                                "+"
                            }
                        }

                        val amount = formatAmount(record.totalAmount)
                        val total = "$symbol $amount"

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            AppText(
                                text = record.name,
                                fontSize = 12.sp
                            )
                            AppText(
                                text = total,
                                fontSize = 12.sp,
                                color = typeColor,
                            )
                        }
                    }

                    item {
                        val totalList = totalsMap.toList()
                        if (totalList.isNotEmpty()) {
                            Spacer(
                                modifier = Modifier
                                    .padding(vertical = 5.dp)
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f))
                            )
                        }
                    }

                    item {
                        val totalList = totalsMap.toList()
                        if (totalList.isNotEmpty()) {
                            Column {
                                for (total in totalList) {
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        AppText(
                                            total.first ?: "",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        AppText(
                                            formatAmount(total.second ?: 0.0),
                                            fontSize = 12.sp,
                                            color = MaterialTheme.colorScheme.onBackground,
                                        )
                                    }
                                }
                            }
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

                    item {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            AppText(
                                "Total de Jornadas",
                                fontSize = 12.sp,
                            )
                            AppText(
                                formatAmount(workingTotal),
                                fontSize = 12.sp,
                                color = green,
                            )
                        }
                    }

                    item {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            AppText(
                                "Total de Deducciones",
                                fontSize = 12.sp,
                            )
                            AppText(
                                formatAmount(deductionTotal),
                                fontSize = 12.sp,
                                color = red,
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
                            AppText(
                                text = currency.name,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Row {
                                AppText(
                                    text = currency.symbol,
                                    color = MaterialTheme.colorScheme.secondary,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(end = 5.dp),
                                )

                                AppText(
                                    text = formatAmount(currency.rate * finalAmount),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}