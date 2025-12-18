package com.joshdev.smartpocket.ui.modules.arching.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joshdev.smartpocket.ui.components.AppText
import com.joshdev.smartpocket.ui.modules.arching.activity.ArchingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryOptionsDialog(viewModel: ArchingViewModel) {
    val sheetState = rememberModalBottomSheetState()

    val onClose = {
        viewModel.toggleCategoryOptions(null, false)
    }

    if (viewModel.showCategoryOptions.value) {
        ModalBottomSheet(
            onDismissRequest = { onClose() },
            sheetState = sheetState,
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                AppText(
                    text = "Opciones de categoría",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize
                )

                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer,
                            contentColor = MaterialTheme.colorScheme.onBackground,
                        ),
                        onClick = {
                            viewModel.deleteCategory()
                            onClose()
                        },
                        modifier = Modifier.padding(top = 10.dp, end = 8.dp)
                    ) {
                        AppText("Eliminar", color = MaterialTheme.colorScheme.onBackground)
                    }
                }
            }
        }
    }
}