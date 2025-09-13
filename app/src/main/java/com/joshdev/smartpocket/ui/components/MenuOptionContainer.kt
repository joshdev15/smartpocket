package com.joshdev.smartpocket.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joshdev.smartpocket.R
import com.joshdev.smartpocket.ui.models.MenuOption
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun MenuOptionContainer(list: List<MenuOption>, onClose: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.8f))
            .padding(top = 200.dp)
    ) {
        LazyColumn {
            items(list) { option ->
                MenuOptionButton(option.icon, option.name, option.onClick)
            }
        }
        Row(
            Modifier.padding(bottom = 100.dp)
        ) {
            MenuOptionButton(R.drawable.close, "Cerrar Menú") { onClose() }
        }
    }
}