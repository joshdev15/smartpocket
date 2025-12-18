package com.joshdev.smartpocket.ui.modules.arching.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joshdev.smartpocket.domain.arching.ArcCategory
import com.joshdev.smartpocket.ui.components.AppText
import com.joshdev.smartpocket.ui.modules.arching.activity.ArchingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewCategoryDialog(viewModel: ArchingViewModel) {
    val sheetState = rememberModalBottomSheetState()

    if (viewModel.showNewCategory.value) {
        var categoryName by remember { mutableStateOf("") }

        ModalBottomSheet(
            onDismissRequest = { viewModel.toggleNewCategory(false) },
            sheetState = sheetState
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                AppText("Nueva Categoría")
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = categoryName,
                    onValueChange = { categoryName = it },
                    label = { Text("Nombre de la categoría") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        val newCategory = ArcCategory(name = categoryName)

                        viewModel.addCategory(newCategory)
                        viewModel.toggleNewCategory(false)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Guardar")
                }
            }
        }
    }
}