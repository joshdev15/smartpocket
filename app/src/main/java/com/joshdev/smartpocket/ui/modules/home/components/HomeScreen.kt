package com.joshdev.smartpocket.ui.modules.home.components

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.ui.Modifier
import com.joshdev.smartpocket.ui.models.HomeOption

@Composable
fun HomeScreen(innerPadding: PaddingValues, options: List<HomeOption>, goToOption: (HomeOption.IDs) -> Unit) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
        ) {
            items(options) {
                HomeOptionsCard(option = it, goToOption = goToOption)
            }
        }
    }
}