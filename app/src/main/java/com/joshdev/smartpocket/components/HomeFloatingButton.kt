package com.joshdev.smartpocket.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun HomeFloatingButton(onClick: () -> Unit) {
    FloatingActionButton(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        onClick = { onClick() }
    ) {
        Icon(
            Icons.Filled.Add,
            contentDescription = "New Invoice",
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}