package com.joshdev.smartpocket.ui.modules.arching.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.joshdev.smartpocket.ui.modules.arching.activity.ArchingViewModel

@Composable
fun ArchingRecordsScreen(
    innerPadding: PaddingValues,
    archingId: String?,
    viewModel: ArchingViewModel
) {
    val arching by viewModel.selectedArching

    LaunchedEffect(archingId) {
        if (archingId != null) {
            viewModel.findArchingById(archingId)
        }
    }

    LazyColumn(modifier = Modifier.padding(innerPadding)) {
        if (arching != null) {
            items(arching!!.records) { record ->
                Text(text = "Día: ${record.dayName}, Semana: ${record.weekOfYear}, Mes: ${record.monthOfYear}")
            }
        }
    }

    NewArchingRecordDialog(viewModel = viewModel)
}
