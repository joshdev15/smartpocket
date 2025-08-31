package com.joshdev.smartpocket.ui.components

import android.annotation.SuppressLint
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.joshdev.smartpocket.ui.theme.displayFontFamily

@Composable
fun AppText(
    text: String,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Normal,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = TextStyle(
            color = color,
            fontSize = fontSize,
            fontWeight = fontWeight,
            fontFamily = displayFontFamily,
        ),
        modifier = modifier,
    )
}