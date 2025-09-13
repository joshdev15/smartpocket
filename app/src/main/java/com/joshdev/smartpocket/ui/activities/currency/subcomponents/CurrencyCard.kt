package com.joshdev.smartpocket.ui.activities.currency.subcomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joshdev.smartpocket.domain.models.Currency
import com.joshdev.smartpocket.ui.components.AppText

@Composable
fun CurrencyCard(currency: Currency, modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        AppText(text = currency.name, modifier = modifier)
        AppText(text = currency.symbol, modifier = modifier)
    }
}