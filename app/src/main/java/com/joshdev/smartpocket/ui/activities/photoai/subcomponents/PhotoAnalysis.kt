package com.joshdev.smartpocket.ui.activities.photoai.subcomponents

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joshdev.smartpocket.ui.activities.photoai.PhotoAIViewModel
import com.joshdev.smartpocket.ui.components.AppText
import kotlin.io.path.Path
import kotlin.io.path.moveTo
import kotlin.math.min

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
                .offset(x = 0.dp, y = (-40).dp)
        ) {
            item {
                Spacer(modifier = Modifier.padding(25.dp))

                Row(
                    modifier = Modifier
                        .fillMaxSize()
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
        }
    }
}