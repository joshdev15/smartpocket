package com.joshdev.smartpocket.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val SWITCH_WIDTH = 250.dp
private val SWITCH_HEIGHT = 40.dp
private val PADDING = 4.dp

@Composable
fun AppSwitch(
    leftOptionText: String,
    rightOptionText: String,
    isChecked: Boolean,
    onChange: (Boolean) -> Unit,
    checkedTextColor: Color = Color.White,
    enableColor: Color = Color(0xFF1E88E5),
    disableColor: Color = Color.LightGray.copy(alpha = 0.3f)
) {
    val offsetX by animateDpAsState(
        targetValue = if (isChecked) {
            (SWITCH_WIDTH / 2) - (PADDING * 2)
        } else {
            0.dp
        },
        animationSpec = tween(durationMillis = 300),
        label = "SwitchOffsetAnimation"
    )

    Box(
        modifier = Modifier
            .width(SWITCH_WIDTH)
            .height(SWITCH_HEIGHT)
            .clip(RoundedCornerShape(SWITCH_HEIGHT / 2))
            .background(disableColor)
            .clickable { onChange(!isChecked) }
            .padding(PADDING)
    ) {
        Box(
            modifier = Modifier
                .offset(x = offsetX)
                .width(SWITCH_WIDTH / 2)
                .height(SWITCH_HEIGHT - (PADDING * 2))
                .clip(CircleShape)
                .background(enableColor)
        )

        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                val textColor = if (!isChecked) checkedTextColor else Color.Black.copy(alpha = 0.3f)
                Text(
                    text = leftOptionText,
                    color = textColor
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                val textColor = if (isChecked) checkedTextColor else Color.Black.copy(alpha = 0.3f)
                Text(
                    text = rightOptionText,
                    color = textColor
                )
            }
        }
    }
}