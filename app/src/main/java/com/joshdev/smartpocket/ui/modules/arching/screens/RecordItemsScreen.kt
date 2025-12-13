package com.joshdev.smartpocket.ui.modules.arching.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.joshdev.smartpocket.ui.components.AppText
import com.joshdev.smartpocket.ui.components.AppTopBarBasic
import com.joshdev.smartpocket.ui.components.FloatingButton
import com.joshdev.smartpocket.ui.modules.arching.activity.ArchingViewModel
import com.joshdev.smartpocket.ui.modules.arching.components.NewItemDialog
import com.joshdev.smartpocket.ui.modules.arching.components.RecordItemCard
import com.joshdev.smartpocket.ui.utils.UiUtils.SCREEN_FLOATING_PADDING
import com.joshdev.smartpocket.ui.utils.UiUtils.SCREEN_PADDING

@Composable
fun RecordItemsScreen(
    recordId: String,
    viewModel: ArchingViewModel
) {
    viewModel.observeRecordsItems(recordId)

    BackHandler {
        viewModel.cleanRecordItemsStates()
    }

    Scaffold(
        topBar = {
            AppTopBarBasic("Elementos de $recordId")
        },
        floatingActionButton = {
            FloatingButton() {
                viewModel.toggleNewItemDialog(true)
            }
        },
    ) { innerPadding ->
//        LaunchedEffect(recordId) {
//            viewModel.findArchingById(recordId)
//        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.inverseOnSurface)
                .padding(innerPadding)
                .padding(horizontal = SCREEN_PADDING)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(MaterialTheme.colorScheme.tertiaryContainer)
                        .padding(10.dp)
                ) {
                    val subTotals = viewModel.recordItems.value.map { recItem ->
                        val localProduct = viewModel.products.value.find { recItem.productId == it.id }
                        localProduct?.let {
                            recItem.quantity.toDouble() * localProduct.price
                        } ?: 0.0
                    }

                    val total = subTotals.sum()

                    AppText("Total: $total")
                }
            }

            itemsIndexed(viewModel.recordItems.value) { idx, innerItems ->
                val product = viewModel.products.value.find { it.id == innerItems.productId }


                Spacer(modifier = Modifier.height(if (idx == 0) SCREEN_PADDING else 0.dp))

                product?.let {
                    RecordItemCard(
                        recordItem = innerItems,
                        product = product,
                        onClick = { },
                        onLongClick = { }
                    )
                }
            }

            item {

            }

            item {
                Spacer(modifier = Modifier.height(SCREEN_FLOATING_PADDING))
            }
        }
    }

    NewItemDialog(viewModel, recordId)
}