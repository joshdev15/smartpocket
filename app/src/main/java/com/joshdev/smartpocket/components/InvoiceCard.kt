package com.joshdev.smartpocket.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joshdev.smartpocket.database.entity.invoice.Invoice

@Composable
fun InvoiceCard(invoice: Invoice) {
    Row(
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .border(1.dp, MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(15.dp))
            .background(MaterialTheme.colorScheme.surfaceBright)
            .padding(20.dp)
    ) {
        Text(
            text = "${invoice.id} ${invoice.name} - ${invoice.author}",
            style = TextStyle(
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = 18.sp,
            )
        )
    }
}