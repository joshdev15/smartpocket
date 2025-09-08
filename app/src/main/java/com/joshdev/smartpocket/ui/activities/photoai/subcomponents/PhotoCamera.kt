package com.joshdev.smartpocket.ui.activities.photoai.subcomponents

import android.content.Context
import android.content.Context.*
import android.hardware.camera2.CameraManager
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getSystemService
import com.joshdev.smartpocket.ui.activities.photoai.PhotoAIViewModel

@Composable

fun PhotoCamera(innerPadding: PaddingValues, viewModel: PhotoAIViewModel) {
    val cameraProviderFuture = viewModel.cameraProviderFuture.value
    val imageCapture = viewModel.imageCapture.value
    val cameraExecutor = viewModel.cameraExecutor.value
    val bitmapImage = viewModel.bitmap.value
    val cameraManager = viewModel.context.value?.let {
        getSystemService(it, CameraManager::class.java)
    }
    val show = remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.padding(innerPadding)
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
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
                if (bitmapImage == null) {
                    CameraPreview(
                        cameraProviderFuture = cameraProviderFuture!!,
                        imageCapture = imageCapture!!,
                        modifier = Modifier
                            .fillMaxSize(),
                    )
                } else {
                    Image(
                        bitmap = bitmapImage!!.asImageBitmap(),
                        contentDescription = "Captured Image",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

        PhotoShutter(
            bitmapImage = bitmapImage,
            onTake = { viewModel.takePhoto(imageCapture!!, cameraExecutor!!) },
            onFlash = {
                cameraManager?.let {
                    show.value = !show.value
                    viewModel.toggleFlashlight(cameraManager, show.value)
                }
            },
            onReject = { viewModel.resetBitmap() },
            onAccept = { viewModel.goToAnalysis() }
        )
    }
}