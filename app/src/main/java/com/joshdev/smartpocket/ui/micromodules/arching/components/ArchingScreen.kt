package com.joshdev.smartpocket.ui.micromodules.arching.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joshdev.smartpocket.ui.micromodules.arching.activity.ArchingViewModel
import com.joshdev.smartpocket.ui.components.ArchingCard
import com.joshdev.smartpocket.ui.components.FastPanel
import com.joshdev.smartpocket.ui.models.FastPanelOption
import com.joshdev.smartpocket.ui.utils.UiUtils

@Composable
fun ArchingScreen(innerPadding: PaddingValues, viewModel: ArchingViewModel) {
    val options = listOf(
        FastPanelOption(
            id = FastPanelOption.IDs.PRODUCTS_ARCHING,
            name = "Productos",
        ),
        FastPanelOption(
            id = FastPanelOption.IDs.CATEGORIES_ARCHING,
            name = "Categorías",
        ),
        FastPanelOption(
            id = FastPanelOption.IDs.CURRENCIES,
            name = "Divisas",
        )
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

        LazyColumn(
            modifier = Modifier.padding(horizontal = UiUtils.SCREEN_PADDING)
        ) {
            itemsIndexed(viewModel.archings.value) { idx, it ->
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(if (idx == 0) UiUtils.SCREEN_PADDING else 0.dp)
                )

                ArchingCard(
                    it,
                    onClick = { viewModel.navigateToRecords(it.id) },
                    onLongClick = { viewModel.toggleArchingOptionsDialog(it, true) }
                )
            }

            item {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(UiUtils.SCREEN_FLOATING_PADDING)
                )
            }
        }
    }

    NewArchingDialog(viewModel)

    ArchingOptionsDialog(viewModel)
}