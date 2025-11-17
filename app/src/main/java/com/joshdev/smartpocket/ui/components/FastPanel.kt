package com.joshdev.smartpocket.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.joshdev.smartpocket.ui.models.FastPanelOption

@Composable
fun FastPanel(buttons: List<FastPanelOption>, onClick: (FastPanelOption.IDs) -> Unit) {
    LazyRow(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primaryContainer,
                        MaterialTheme.colorScheme.tertiaryContainer,
                    )
                ),
                shape = RoundedCornerShape(0.dp)
            )
            .padding(vertical = 10.dp)
    ) {
        itemsIndexed(buttons) { idx, item ->
            Spacer(modifier = Modifier.width(if (idx == 0) 10.dp else 0.dp))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .clickable(onClick = {
                        onClick(item.id)
                    })
                    .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
                    .padding(vertical = 5.dp, horizontal = 10.dp)
            ) {
                AppText(
                    item.name,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.background
                )
            }
        }
    }
}