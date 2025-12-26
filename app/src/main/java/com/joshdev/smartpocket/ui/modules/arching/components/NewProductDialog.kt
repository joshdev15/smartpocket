package com.joshdev.smartpocket.ui.modules.arching.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.joshdev.smartpocket.domain.arching.ArcProduct
import com.joshdev.smartpocket.ui.components.AppText
import com.joshdev.smartpocket.ui.modules.arching.activity.ArchingViewModel
import com.joshdev.smartpocket.ui.utils.UiUtils.hexToColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewArchingProductDialog(viewModel: ArchingViewModel) {
    val sheetState = rememberModalBottomSheetState()
    var proName by remember { mutableStateOf("") }
    var proPrice by remember { mutableStateOf("") }
    var selectedCategoryId: String? by remember { mutableStateOf(null) }
    var categoryExpanded by remember { mutableStateOf(false) }

    val categories = viewModel.categories.value

    val onClose = {
        viewModel.toggleNewProductDialog(false)
        proName = ""
        proPrice = ""
        selectedCategoryId = null
    }

    if (viewModel.showNewProductDialog.value) {
        ModalBottomSheet(
            onDismissRequest = { onClose() },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                AppText(
                    text = "Nueva Producto",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize
                )

                OutlinedTextField(
                    value = proName,
                    onValueChange = { proName = it },
                    label = { AppText("Nombre de Producto") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                )

                OutlinedTextField(
                    value = proPrice,
                    onValueChange = { proPrice = it },
                    label = { AppText("Costo Individual") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                ) {
                    // Recuadro clicable que simula el campo y muestra el color
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                1.dp,
                                MaterialTheme.colorScheme.outline,
                                RoundedCornerShape(4.dp)
                            )
                            .clickable { categoryExpanded = !categoryExpanded }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            val selectedCategory = categories.find { it.id.toString() == selectedCategoryId }
                            val categoryName = selectedCategory?.name ?: "Sin Categoría"
                            val categoryColorHex = selectedCategory?.color
                            
                            AppText(text = categoryName)

                            if (categoryColorHex != null) {
                                val localColor: Color = hexToColor(categoryColorHex)
                                Box(
                                    modifier = Modifier
                                        .padding(start = 8.dp)
                                        .size(10.dp)
                                        .clip(CircleShape)
                                        .background(localColor)
                                )
                            }
                        }

                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = "Dropdown Arrow",
                            modifier = Modifier.rotate(if (categoryExpanded) 180f else 0f)
                        )
                    }

                    if (categoryExpanded) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    1.dp,
                                    MaterialTheme.colorScheme.outline,
                                    RoundedCornerShape(4.dp)
                                )
                                .padding(vertical = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedCategoryId = null
                                        categoryExpanded = false
                                    }
                                    .padding(16.dp)
                            ) {
                                AppText("Sin Categoría")
                            }

                            categories.forEach { category ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            selectedCategoryId = category.id.toString()
                                            categoryExpanded = false
                                        }
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    AppText(category.name)

                                    val localColor: Color = hexToColor(category.color)
                                    Box(
                                        modifier = Modifier
                                            .padding(start = 8.dp)
                                            .size(10.dp)
                                            .clip(CircleShape)
                                            .background(localColor)
                                    )
                                }
                            }
                        }
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            try {
                                val pro = ArcProduct(
                                    name = proName,
                                    price = proPrice.toDouble(),
                                    categoryId = selectedCategoryId ?: "",
                                )

                                viewModel.addProduct(pro)
                                onClose()
                            } catch (e: Exception) {
                                onClose()
                                e.printStackTrace()
                            }
                        },
                        modifier = Modifier
                            .width(100.dp)
                            .padding(top = 10.dp)
                    ) {
                        AppText("Crear")
                    }
                }
            }
        }
    }
}