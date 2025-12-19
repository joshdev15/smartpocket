package com.joshdev.smartpocket.ui.modules.arching.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joshdev.smartpocket.ui.components.AppTopBarBasic
import com.joshdev.smartpocket.ui.components.FloatingButton
import com.joshdev.smartpocket.ui.modules.arching.activity.ArchingViewModel
import com.joshdev.smartpocket.ui.modules.arching.components.NewRecordDialog
import com.joshdev.smartpocket.ui.modules.arching.components.RecordCard
import com.joshdev.smartpocket.ui.modules.arching.components.RecordOptionsDialog
import com.joshdev.smartpocket.ui.modules.arching.components.RecordTotalizer
import com.joshdev.smartpocket.ui.utils.UiUtils.SCREEN_FLOATING_PADDING
import com.joshdev.smartpocket.ui.utils.UiUtils.SCREEN_PADDING

@Composable
fun RecordsScreen(
    archingId: Long,
    viewModel: ArchingViewModel
) {
    viewModel.observeRecords(archingId)

    BackHandler {
        viewModel.cleanRecordStates()
    }

    Scaffold(
        topBar = {
            AppTopBarBasic("Cierre - ${viewModel.currentArching.value?.name ?: ""}")
        },
        floatingActionButton = {
            FloatingButton {
                viewModel.addRecord(archingId)
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.inverseOnSurface)
                .padding(innerPadding)

        ) {
            val title = if (viewModel.currentArching.value?.name != null) {
                "Totales de ${viewModel.currentArching.value?.name}"
            } else {
                "Totales"
            }

            RecordTotalizer(
                title,
                viewModel.records.value,
                viewModel.currencies.value
            )

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
                            record.id?.let { recordId ->
                                viewModel.navToRecordItem(recordId)
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
    }
}