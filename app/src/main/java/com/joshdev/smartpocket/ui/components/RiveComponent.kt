package com.joshdev.smartpocket.ui.components

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import app.rive.runtime.kotlin.RiveAnimationView
import com.joshdev.smartpocket.R

@Composable
fun RiveAnimation(id: Int, modifier: Modifier = Modifier) {
    AndroidView(
        factory = { context ->
            RiveAnimationView(context).apply {
                setRiveResource(id)
                play()
            }
        },
        update = {},
        modifier = modifier
    )
}