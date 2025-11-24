package com.joshdev.smartpocket.ui.micromodules.ledger.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joshdev.smartpocket.ui.components.AppText

@Composable
fun DescriptorWithColor(
    color: Color,
    text: String,
    amount: Double
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(
            modifier = Modifier
                .width(10.dp)
                .height(10.dp)
                .clip(RoundedCornerShape(2.dp))
                .border(1.dp, MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.5f))
                .background(color = color)
        )
        Spacer(
            modifier = Modifier
                .width(10.dp)
                .height(10.dp)
        )
        AppText("$text: $amount", fontSize = 10.sp)
    }
}