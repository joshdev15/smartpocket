package com.joshdev.smartpocket.ui.activities.currency.subcomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.joshdev.smartpocket.ui.activities.currency.CurrencyViewModel
import com.joshdev.smartpocket.ui.components.AppText

@Composable
fun CurrencyScreen(innerPadding: PaddingValues, viewModel: CurrencyViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(innerPadding)
    ) {
        AppText("Hola")
    }
}