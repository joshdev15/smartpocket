package com.joshdev.smartpocket.ui.activities.arching.subcomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.joshdev.smartpocket.ui.activities.arching.ArchingViewModel
import com.joshdev.smartpocket.ui.components.AppText
import com.joshdev.smartpocket.ui.components.FastPanel
import com.joshdev.smartpocket.ui.models.FastPanelOption

@Composable
fun ArchingScreen(innerPadding: PaddingValues, viewModel: ArchingViewModel) {
    val options = listOf(
        FastPanelOption(
            id = FastPanelOption.IDs.PRODUCTS,
            name = "Productos",
        ),
        FastPanelOption(
            id = FastPanelOption.IDs.CATEGORIES_ARCHING,
            name = "Categorías de Cierre",
        ),
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.inverseOnSurface)
            .padding(innerPadding)
    ) {
        FastPanel(options) { id ->
            viewModel.goTo(id)
        }

        LazyColumn {
            item {
                AppText("Arqueo")
            }
        }
    }
}