package com.joshdev.smartpocket.activities.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joshdev.smartpocket.components.InvoiceCard
import com.joshdev.smartpocket.database.entity.invoice.Invoice

@Composable
fun HomeMainView(innerPadding: PaddingValues, invoices: List<Invoice>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(innerPadding)
    ) {
        LazyColumn(
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            itemsIndexed(invoices) { idx, it ->
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(if (idx == 0) 10.dp else 0.dp)
                )

                InvoiceCard(it)
            }
        }
    }
}