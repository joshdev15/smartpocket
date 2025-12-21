package com.joshdev.smartpocket.ui.modules.arching.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joshdev.smartpocket.ui.components.AppTopBarBasic
import com.joshdev.smartpocket.ui.components.ArchingCard
import com.joshdev.smartpocket.ui.components.FastPanel
import com.joshdev.smartpocket.ui.components.FloatingButton
import com.joshdev.smartpocket.ui.models.FastPanelOption
import com.joshdev.smartpocket.ui.modules.arching.activity.ArchingViewModel
import com.joshdev.smartpocket.ui.modules.arching.components.ArchingOptionsDialog
import com.joshdev.smartpocket.ui.modules.arching.components.NewArchingDialog
import com.joshdev.smartpocket.ui.utils.UiUtils.SCREEN_FLOATING_PADDING
import com.joshdev.smartpocket.ui.utils.UiUtils.SCREEN_PADDING

@Composable
fun ArchingScreen(viewModel: ArchingViewModel) {
    val options = listOf(
        FastPanelOption(
            id = FastPanelOption.IDs.PRODUCTS_ARCHING,
            name = "Productos de Cierre",
        ),
        FastPanelOption(
            id = FastPanelOption.IDs.CATEGORIES_ARCHING,
            name = "Categorías de Cierre",
        ),
        FastPanelOption(
            id = FastPanelOption.IDs.CURRENCIES,
            name = "Divisas",
        )
    )

    Scaffold(
        topBar = {
            AppTopBarBasic("Cierres")
        },
        floatingActionButton = {
            FloatingButton("Nuevo Cierre") {
                viewModel.toggleNewArchingDialog(true)
            }
        },
        content = { innerPadding ->
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
                    modifier = Modifier.padding(horizontal = SCREEN_PADDING)
                ) {
                    itemsIndexed(viewModel.archingList.value) { idx, it ->
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(if (idx == 0) SCREEN_PADDING else 0.dp)
                        )

                        ArchingCard(
                            it,
                            onClick = {
                                it.id?.let { recordId ->
                                    viewModel.navToRecords(recordId)
                                }
                            },
                            onLongClick = { viewModel.toggleArchingOptionsDialog(it, true) }
                        )
                    }

                    item {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(SCREEN_FLOATING_PADDING)
                        )
                    }
                }
            }

            NewArchingDialog(viewModel)
            ArchingOptionsDialog(viewModel)
        }
    )
}