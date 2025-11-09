package com.joshdev.smartpocket.ui.activities.ledger.subcomponents

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.joshdev.smartpocket.R

@Composable
fun BarChart(
    totalBalance: Double,
    income: Double,
    egress: Double,
) {
    val capitalColor = MaterialTheme.colorScheme.background
    val incomeColor = colorResource(id = R.color.income)
    val egressColor = colorResource(id = R.color.egress)

    Canvas(
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth()
            .height(15.dp)
    ) {
        val barHeight = size.height
        val totalWidth = size.width
        val cornerRadius = CornerRadius(30f, 30f)

        drawRoundRect(
            color = capitalColor.copy(alpha = 0.3f),
            topLeft = Offset(0f, 0f),
            size = Size(totalWidth, barHeight),
            cornerRadius = cornerRadius
        )

        val incomeWidth = (income / totalBalance).toFloat() * totalWidth
        drawRoundRect(
            color = incomeColor.copy(alpha = 0.8f),
            topLeft = Offset(totalWidth - incomeWidth, 0f),
            size = Size(incomeWidth, barHeight),
            cornerRadius = cornerRadius
        )

        val egressWidth = (egress / totalBalance).toFloat() * totalWidth
        drawRoundRect(
            color = egressColor.copy(alpha = 0.8f),
            topLeft = Offset(0f, 0f),
            size = Size(egressWidth, barHeight),
            cornerRadius = cornerRadius
        )
    }
}