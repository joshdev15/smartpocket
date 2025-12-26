package com.joshdev.smartpocket.ui.modules.arching.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.joshdev.smartpocket.domain.arching.ArcCategory
import com.joshdev.smartpocket.ui.components.AppText
import com.joshdev.smartpocket.ui.modules.arching.activity.ArchingViewModel
import com.joshdev.smartpocket.ui.utils.UiUtils.hexToColor

@Composable
fun ColorPickerSelector(
    selectedColorHex: String,
    onColorSelected: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        AppText("Selecciona un color")
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            items(CATEGORY_COLORS) { colorHex ->
                val isSelected = colorHex == selectedColorHex
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clip(CircleShape)
                        .size(40.dp)
                        .background(hexToColor(colorHex))
                        .clickable { onColorSelected(colorHex) }
                        .then(
                            if (isSelected) {
                                Modifier.border(
                                    BorderStroke(3.dp, MaterialTheme.colorScheme.onBackground),
                                    CircleShape
                                )
                            } else {
                                Modifier
                            }
                        )
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewCategoryDialog(viewModel: ArchingViewModel) {
    val sheetState = rememberModalBottomSheetState()

    if (viewModel.showNewCategory.value) {
        var categoryName by remember { mutableStateOf("") }
        var categoryColor by remember { mutableStateOf("#E6194B") }

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

                ColorPickerSelector(
                    selectedColorHex = categoryColor,
                    onColorSelected = { categoryColor = it }
                )

                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        val newCategory = ArcCategory(name = categoryName, color = categoryColor)

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

private val CATEGORY_COLORS = listOf(
    "#E6194B", "#3CB44B", "#FFE119", "#4363D8", "#F58231",
    "#911EB4", "#42D4F4", "#F032E6", "#BFEF45", "#FABEBE",
    "#469990", "#E6BEFF", "#9A6324", "#FFFAC8", "#800000",
    "#AAFFC3", "#808000", "#FFD8B1", "#000075", "#A9A9A9"
)
