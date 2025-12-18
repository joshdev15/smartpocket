package com.joshdev.smartpocket.ui.modules.ledger.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.joshdev.smartpocket.ui.components.AppTopBarBasic
import com.joshdev.smartpocket.ui.components.FloatingButton
import com.joshdev.smartpocket.ui.modules.ledger.activity.LedgerViewModel
import com.joshdev.smartpocket.ui.utils.UiUtils.SCREEN_FLOATING_PADDING
import com.joshdev.smartpocket.ui.utils.UiUtils.SCREEN_PADDING

@Composable
fun CategoriesScreen(viewModel: LedgerViewModel) {
    Scaffold(
        topBar = { AppTopBarBasic("Categorías de Cuentas") },
        floatingActionButton = {
            FloatingButton("Nueva Categoría") {
                viewModel.toggleNewCategory(true)
            }
        },
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.inverseOnSurface)
                    .padding(innerPadding)
                    .padding(horizontal = SCREEN_PADDING)
                    .padding(top = SCREEN_PADDING, bottom = SCREEN_FLOATING_PADDING)
            ) {

//                itemsIndexed() { idx, it ->
//                }
            }
        }
    )
}