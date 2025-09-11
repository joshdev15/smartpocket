package com.joshdev.smartpocket.ui.activities.photoai.subcomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joshdev.smartpocket.ui.activities.photoai.PhotoAIViewModel

@Composable
fun PhotoEditor(innerPadding: PaddingValues, viewModel: PhotoAIViewModel) {
    val selectedText = remember { mutableStateOf("") }
    LaunchedEffect(viewModel.result.value) {
        viewModel.result.value.let {
            it.forEach { block ->
                selectedText.value += block?.block
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .padding(top = 15.dp)
            .padding(horizontal = 10.dp)
    ) {
        Row() {
            OutlinedTextField(
                value = selectedText.value,
                onValueChange = { selectedText.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 5.dp),
            )
        }
    }
}