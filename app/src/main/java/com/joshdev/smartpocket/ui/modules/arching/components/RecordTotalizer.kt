package com.joshdev.smartpocket.ui.modules.arching.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joshdev.smartpocket.domain.arching.ArcRecord
import com.joshdev.smartpocket.domain.currency.Currency
import com.joshdev.smartpocket.ui.components.AppText
import com.joshdev.smartpocket.ui.utils.UiUtils.formatAmount

@Composable
fun RecordTotalizer(
    title: String,
    recordList: List<ArcRecord>,
    currencyList: List<Currency>
) {
    val finalAmount = recordList.sumOf { it.totalAmount }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            .padding(10.dp)
            .width(70.dp)
            .height(125.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            AppText(title)
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
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                }
            }

            items(recordList) { record ->
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AppText(record.dayName, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    AppText(
                        formatAmount(record.totalAmount),
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
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.padding(end = 5.dp)
                        )

                        AppText(
                            formatAmount(currency.rate * finalAmount),
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}