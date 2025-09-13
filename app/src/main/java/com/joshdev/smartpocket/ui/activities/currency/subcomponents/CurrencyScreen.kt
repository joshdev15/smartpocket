package com.joshdev.smartpocket.ui.activities.currency.subcomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joshdev.smartpocket.domain.models.Currency
import com.joshdev.smartpocket.ui.activities.currency.CurrencyViewModel

@Composable
fun CurrencyScreen(innerPadding: PaddingValues, viewModel: CurrencyViewModel) {
    val usd = Currency(name = "USD", symbol = "$", rate = 1.0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        LazyColumn(modifier = Modifier.padding(horizontal = 10.dp)) {
            item {
                CurrencyCard(usd)
            }

            items(viewModel.currencies.value ?: emptyList()) {
                CurrencyCard(it)
            }
        }
    }
}