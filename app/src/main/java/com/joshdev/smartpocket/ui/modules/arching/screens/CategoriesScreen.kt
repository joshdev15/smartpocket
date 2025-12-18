package com.joshdev.smartpocket.ui.modules.arching.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.joshdev.smartpocket.ui.components.AppText
import com.joshdev.smartpocket.ui.components.AppTopBarBasic
import com.joshdev.smartpocket.ui.components.FloatingButton
import com.joshdev.smartpocket.ui.modules.arching.activity.ArchingViewModel
import com.joshdev.smartpocket.ui.modules.arching.components.CategoryOptionsDialog
import com.joshdev.smartpocket.ui.modules.arching.components.NewCategoryDialog
import com.joshdev.smartpocket.ui.utils.UiUtils.SCREEN_FLOATING_PADDING
import com.joshdev.smartpocket.ui.utils.UiUtils.SCREEN_PADDING

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoriesScreen(viewModel: ArchingViewModel) {
    val haptic = LocalHapticFeedback.current
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

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.inverseOnSurface)
                    .padding(innerPadding)
                    .padding(horizontal = SCREEN_PADDING)
                    .padding(top = SCREEN_PADDING, bottom = SCREEN_FLOATING_PADDING)
            ) {
                itemsIndexed(viewModel.categories.value) { _, category ->
                    AppText(
                        text = category.name,
                        modifier = Modifier.combinedClickable(
                            onClick = { },
                            onLongClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                viewModel.toggleCategoryOptions(category, true)
                            }
                        ).padding(vertical = 8.dp)
                    )
                }
            }
        }
    )
}