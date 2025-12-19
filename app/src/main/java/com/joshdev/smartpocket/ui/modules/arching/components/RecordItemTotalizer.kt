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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joshdev.smartpocket.domain.arching.ArcProduct
import com.joshdev.smartpocket.domain.arching.ArcRecordItem
import com.joshdev.smartpocket.domain.currency.Currency
import com.joshdev.smartpocket.ui.components.AppText
import com.joshdev.smartpocket.ui.utils.UiUtils.formatAmount

@Composable
fun RecordItemTotalizer(
    title: String,
    itemList: List<ArcRecordItem>,
    arcProductList: List<ArcProduct>,
    currencyList: List<Currency>
) {
    val subTotals = itemList.map { recItem ->
        val currentProduct = arcProductList.find { recItem.productId == it.id }
        currentProduct?.let {
            recItem.quantity.toDouble() * currentProduct.price
        } ?: 0.0
    }

    val totalQuantities = itemList.sumOf { it.quantity }

    val total = subTotals.sum()

    Column(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(10.dp)
            .width(70.dp)
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

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AppText("Cant. Productos", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            AppText(totalQuantities.toString(), fontSize = 12.sp)
        }

        Spacer(
            modifier = Modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f))
        )

        currencyList.forEach { currency ->
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