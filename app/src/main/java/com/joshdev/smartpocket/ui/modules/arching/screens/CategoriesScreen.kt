package com.joshdev.smartpocket.ui.modules.arching.screens

import androidx.compose.foundation.ExperimentalFoundationApi
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
import com.joshdev.smartpocket.ui.components.FloatingButton
import com.joshdev.smartpocket.ui.modules.arching.activity.ArchingViewModel
import com.joshdev.smartpocket.ui.modules.arching.components.CategoryCard
import com.joshdev.smartpocket.ui.modules.arching.components.CategoryOptionsDialog
import com.joshdev.smartpocket.ui.modules.arching.components.NewCategoryDialog

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoriesScreen(viewModel: ArchingViewModel) {
    viewModel.observeCategories()

    Scaffold(
        topBar = { AppTopBarBasic("Categorías de Cierre") },
        floatingActionButton = {
            FloatingButton("Nueva Categoría") {
                viewModel.toggleNewCategory(true)
            }
        },
        content = { innerPadding ->
            NewCategoryDialog(viewModel = viewModel)
            CategoryOptionsDialog(viewModel = viewModel)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.inverseOnSurface)
                    .padding(innerPadding)
                    .padding(horizontal = 10.dp)
            ) {
                LazyColumn {
                    itemsIndexed(viewModel.categories.value) { idx, category ->
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(if (idx == 0) 15.dp else 0.dp)
                        )

                        CategoryCard(
                            arcCategory = category,
                            onLongClick = {
                                viewModel.toggleCategoryOptions(category, true)
                            }
                        )
                    }

                    item {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(140.dp)
                        )
                    }
                }
            }
        }
    )
}