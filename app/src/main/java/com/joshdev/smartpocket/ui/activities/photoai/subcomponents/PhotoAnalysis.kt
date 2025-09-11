package com.joshdev.smartpocket.ui.activities.photoai.subcomponents

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joshdev.smartpocket.ui.activities.photoai.PhotoAIViewModel
import com.joshdev.smartpocket.ui.components.AppText

@Composable
fun PhotoAnalysis(innerPadding: PaddingValues, viewModel: PhotoAIViewModel) {
    viewModel.analyzeImage()
    val selected = remember { mutableStateListOf<Int>() }

    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.padding(innerPadding)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 10.dp, bottom = 10.dp)
                        .clip(RoundedCornerShape(40.dp))
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.secondaryContainer,
                            RoundedCornerShape(40.dp)
                        )
                ) {
                    viewModel.bitmap.value?.let {
                        Image(
                            bitmap = viewModel.bitmap.value!!.asImageBitmap(),
                            contentDescription = "Captured Image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }

            itemsIndexed(viewModel.result.value) { idx, block ->
                block?.let {
                    Row(
                        modifier = Modifier
                            .padding(5.dp)
                            .clickable(
                                onClick = {
                                    if (selected.contains(block.order)) {
                                        selected.remove(block.order)
                                    } else {
                                        selected.add(block.order)
                                    }
                                }
                            )
                            .border(
                                1.dp,
                                if (selected.contains(block.order)) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer,
                                RoundedCornerShape(4.dp)
                            )
                            .padding(4.dp)
                    ) {
                        AppText(text = block.block, fontSize = 10.sp)
                    }
                }
            }

            item {
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                ) {
                    Button(
                        enabled = selected.isNotEmpty(),
                        onClick = {
                            viewModel.setResultToEdit(selected)
                            viewModel.goToEdition()
                        }
                    ) {
                        AppText(
                            "Continuar con (${selected.size}) elementos seleccionados",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}