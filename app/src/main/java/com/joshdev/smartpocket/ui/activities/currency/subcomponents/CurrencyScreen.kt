package com.joshdev.smartpocket.ui.activities.currency.subcomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joshdev.smartpocket.ui.activities.currency.CurrencyViewModel

@Composable
fun CurrencyScreen(innerPadding: PaddingValues, viewModel: CurrencyViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(MaterialTheme.colorScheme.inverseOnSurface)
    ) {
        LazyColumn(modifier = Modifier.padding(horizontal = 10.dp)) {
            itemsIndexed(viewModel.currencies.value ?: emptyList()) { idx, it ->
                Spacer(
                    modifier = Modifier
                        .height(if (idx == 0) 15.dp else 0.dp)
                        .padding(bottom = 10.dp)
                        .fillMaxSize()
                )

                CurrencyCard(it)
            }
        }
    }
}