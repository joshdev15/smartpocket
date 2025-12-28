package com.joshdev.smartpocket.ui.modules.arching.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joshdev.smartpocket.ui.components.AppText
import com.joshdev.smartpocket.ui.components.AppTopBarBasic
import com.joshdev.smartpocket.ui.components.FloatingButton
import com.joshdev.smartpocket.ui.modules.arching.activity.ArchingViewModel
import com.joshdev.smartpocket.ui.modules.arching.components.NewItemDialog
import com.joshdev.smartpocket.ui.modules.arching.components.RecordItemCard
import com.joshdev.smartpocket.ui.modules.arching.components.RecordItemTotalizerDialog
import com.joshdev.smartpocket.ui.utils.UiUtils.SCREEN_FLOATING_PADDING
import com.joshdev.smartpocket.ui.utils.UiUtils.SCREEN_PADDING

@Composable
fun RecordItemsScreen(
    recordId: Long,
    viewModel: ArchingViewModel
) {
    LaunchedEffect(recordId) {
        viewModel.observeRecordsItems(recordId)
    }

    BackHandler {
        viewModel.cleanRecordItemsStates()
    }

    Scaffold(
        topBar = {
            AppTopBarBasic("Elementos de ${viewModel.currentRecord.value?.name}")
        },
        floatingActionButton = {
            FloatingButton {
                viewModel.toggleNewItemDialog(true)
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.inverseOnSurface)
                .padding(innerPadding)
        ) {
            val title = if (viewModel.currentRecord.value?.name != null) {
                "Elementos de ${viewModel.currentRecord.value?.name}"
            } else {
                "Elementos"
            }

//            RecordItemTotalizer(
//                title,
//                viewModel.recordItems.value,
//                viewModel.products.value,
//                viewModel.currencies.value,
//                viewModel.itemTotalsMap.value
//            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .padding(top = 10.dp)
            ) {
                Button(
                    colors = ButtonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onBackground,
                        disabledContainerColor = MaterialTheme.colorScheme.surfaceBright.copy(alpha = 0.5f),
                        disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { viewModel.toggleItemTotalizer(true) }
                ) {
                    AppText("Ver Totales")
                }
            }

            LazyColumn(
                contentPadding = PaddingValues(
                    top = 0.dp,
                    start = SCREEN_PADDING,
                    end = SCREEN_PADDING,
                    bottom = 0.dp
                )
            ) {
                itemsIndexed(viewModel.recordItems.value) { idx, innerItem ->
                    val product = viewModel.products.value.find { it.id == innerItem.productId }

                    Spacer(modifier = Modifier.height(if (idx == 0) SCREEN_PADDING else 0.dp))

                    product?.let {
                        RecordItemCard(
                            arcRecordItem = innerItem,
                            arcProduct = product,
                            onClick = {},
                            onLongClick = {
                                viewModel.toggleItemOptionsDialog(innerItem, true)
                            }
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(SCREEN_FLOATING_PADDING))
                }
            }
        }
    }

    NewItemDialog(viewModel, recordId)
    RecordItemTotalizerDialog(viewModel)
}