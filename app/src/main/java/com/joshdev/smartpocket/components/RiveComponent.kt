package com.joshdev.smartpocket.components

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import app.rive.runtime.kotlin.RiveAnimationView
import com.joshdev.smartpocket.R

@Composable
fun RiveAnimation(modifier: Modifier = Modifier) {
    AndroidView(
        factory = { context ->
            RiveAnimationView(context).apply {
                setRiveResource(R.raw.coqui)
                play()
            }
        },
        update = {},
        modifier = modifier.background(Color.Gray)
    )
}