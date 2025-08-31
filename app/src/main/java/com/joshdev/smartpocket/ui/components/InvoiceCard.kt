package com.joshdev.smartpocket.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joshdev.smartpocket.domain.models.Invoice
import com.joshdev.smartpocket.ui.utils.UiUtils.formatAmount
import com.joshdev.smartpocket.ui.utils.UiUtils.formatDate

@Composable
fun InvoiceCard(invoice: Invoice, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(30.dp))
            .clickable(onClick = { onClick() })
            .border(2.dp, MaterialTheme.colorScheme.tertiaryContainer, RoundedCornerShape(30.dp))
            .background(MaterialTheme.colorScheme.inverseOnSurface)
            .padding(20.dp)
    ) {
        Column {
            AppText(
                text = invoice.name,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                AppText(
                    text = "ID: ${invoice.id}",
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 12.sp,
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                AppText(
                    text = "Total: ${formatAmount(invoice.total ?: 0.0)}",
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 12.sp,
                )
                AppText(
                    text = formatDate(invoice.creationDate),
                    color = MaterialTheme.colorScheme.tertiary,
                    fontSize = 12.sp,
                )
            }
        }
    }
}