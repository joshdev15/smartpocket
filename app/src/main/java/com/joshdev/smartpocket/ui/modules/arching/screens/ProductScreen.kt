package com.joshdev.smartpocket.ui.modules.arching.screens

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
import com.joshdev.smartpocket.ui.modules.arching.components.ProductCard
import com.joshdev.smartpocket.ui.modules.arching.components.NewArchingProductDialog
import com.joshdev.smartpocket.ui.components.AppTopBarBasic
import com.joshdev.smartpocket.ui.components.FloatingButton
import com.joshdev.smartpocket.ui.modules.arching.activity.ArchingViewModel

@Composable
fun ProductScreen(viewModel: ArchingViewModel) {
    Scaffold(
        topBar = { AppTopBarBasic("Productos de Cierre") },
        floatingActionButton = {
            FloatingButton(
                "Agregar Producto",
            ) {
                viewModel.toggleNewProductDialog(true)
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.inverseOnSurface)
                    .padding(innerPadding)
                    .padding(horizontal = 10.dp)
            ) {
                LazyColumn {
                    itemsIndexed(viewModel.products.value) { idx, product ->
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(if (idx == 0) 15.dp else 0.dp)
                        )

                        ProductCard(product)
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

            NewArchingProductDialog(viewModel = viewModel)
        }
    )

}