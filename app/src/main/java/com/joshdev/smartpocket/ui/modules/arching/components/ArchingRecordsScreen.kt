package com.joshdev.smartpocket.ui.modules.arching.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joshdev.smartpocket.ui.components.AppTopBarBasic
import com.joshdev.smartpocket.ui.components.FloatingButton
import com.joshdev.smartpocket.ui.modules.arching.activity.ArchingViewModel
import com.joshdev.smartpocket.ui.utils.UiUtils.SCREEN_FLOATING_PADDING
import com.joshdev.smartpocket.ui.utils.UiUtils.SCREEN_PADDING

@Composable
fun ArchingRecordsScreen(
    archingId: String,
    viewModel: ArchingViewModel
) {
    Scaffold(
        topBar = {
            AppTopBarBasic("Cierre - $archingId")
        },
        floatingActionButton = {
            FloatingButton() {
                viewModel.addArchingRecord(archingId)
            }
        },
    ) { innerPadding ->
        LaunchedEffect(archingId) {
            viewModel.findArchingById(archingId)
        }

        LazyVerticalGrid(
            horizontalArrangement = Arrangement.spacedBy(SCREEN_PADDING),
            verticalArrangement = Arrangement.spacedBy(SCREEN_PADDING),
            contentPadding = PaddingValues(top = SCREEN_PADDING, start = SCREEN_PADDING, end = SCREEN_PADDING, bottom = 0.dp),
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.inverseOnSurface)
                .padding(innerPadding)
        ) {
            items(viewModel.records.value) { record ->
                ArchingRecordCard(
                    record = record,
                    onClick = {},
                    onLongClick = {
                        viewModel.toggleArchingRecordOptionsDialog(record, true)
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(SCREEN_FLOATING_PADDING))
            }
        }

        NewArchingRecordDialog(viewModel, archingId)
        ArchingRecordOptionsDialog(viewModel)
    }
}