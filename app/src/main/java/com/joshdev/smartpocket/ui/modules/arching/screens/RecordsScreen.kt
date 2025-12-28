package com.joshdev.smartpocket.ui.modules.arching.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joshdev.smartpocket.domain.arching.ArcRecord
import com.joshdev.smartpocket.ui.components.AppText
import com.joshdev.smartpocket.ui.components.AppTopBarBasic
import com.joshdev.smartpocket.ui.components.FastPanel
import com.joshdev.smartpocket.ui.components.FloatingButton
import com.joshdev.smartpocket.ui.models.FastPanelOption
import com.joshdev.smartpocket.ui.modules.arching.activity.ArchingViewModel
import com.joshdev.smartpocket.ui.modules.arching.components.NewRecordDialog
import com.joshdev.smartpocket.ui.modules.arching.components.RecordCard
import com.joshdev.smartpocket.ui.modules.arching.components.RecordOptionsDialog
import com.joshdev.smartpocket.ui.modules.arching.components.RecordTotalizerDialog
import com.joshdev.smartpocket.ui.utils.UiUtils.SCREEN_FLOATING_PADDING
import com.joshdev.smartpocket.ui.utils.UiUtils.SCREEN_PADDING

@Composable
fun RecordsScreen(
    archingId: Long,
    viewModel: ArchingViewModel
) {
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

    LaunchedEffect(archingId) {
        viewModel.observeRecords(archingId)
        viewModel.calculateArchingTotalAmount(archingId)
    }

    BackHandler {
        viewModel.cleanRecordStates()
    }

    Scaffold(
        topBar = {
            AppTopBarBasic("Cierre - ${viewModel.currentArching.value?.name ?: ""}")
        },
        floatingActionButton = {
            FloatingButton {
                viewModel.toggleNewRecordDialog(true)
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.inverseOnSurface)
                .padding(innerPadding)
        ) {
            FastPanel(options) { viewModel.goTo(it) }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .padding(top = 10.dp)
            ) {
                Button(
                    colors = ButtonColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        contentColor = MaterialTheme.colorScheme.onBackground,
                        disabledContainerColor = MaterialTheme.colorScheme.surfaceBright.copy(alpha = 0.5f),
                        disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { viewModel.toggleRecordTotalizerDialog(true) }
                ) {
                    AppText("Ver Totales")
                }
            }

            LazyColumn(
                contentPadding = PaddingValues(
                    top = SCREEN_PADDING,
                    start = SCREEN_PADDING,
                    end = SCREEN_PADDING,
                    bottom = 0.dp
                ),
            ) {
                items(viewModel.records.value) { record ->
                    RecordCard(
                        arcRecord = record,
                        onClick = {
                            if (record.type == ArcRecord.RecType.WorkingDay) {
                                record.id?.let { recordId ->
                                    viewModel.navToRecordItem(recordId)
                                }
                            } else {
                                viewModel.toast("Una deducción no tiene detalle")
                            }
                        },
                        onLongClick = {
                            viewModel.toggleRecordOptionsDialog(record, true)
                        }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(SCREEN_FLOATING_PADDING))
                }
            }
        }

        NewRecordDialog(viewModel, archingId)
        RecordOptionsDialog(viewModel)
        RecordTotalizerDialog(viewModel)
    }
}