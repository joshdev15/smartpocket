package com.joshdev.smartpocket.ui.activities.photoai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import com.joshdev.smartpocket.R
import com.joshdev.smartpocket.ui.components.AppBasicTopBar
import com.joshdev.smartpocket.ui.theme.SmartPocketTheme
import java.util.concurrent.Executor

class PhotoAIActivity : ComponentActivity() {
    private val viewModel by viewModels<PhotoAIViewModel>()
    private var invoiceId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.getIntExtra("invoiceId", -1)?.let {
            invoiceId = it
        }

        viewModel.start(this, this@PhotoAIActivity, invoiceId)

        setContent {
            val cameraProviderFuture = viewModel.cameraProviderFuture.value
            val imageCapture = viewModel.imageCapture.value
            val cameraExecutor = viewModel.cameraExecutor.value
            val bitmapImage = viewModel.bitmap.value

            SmartPocketTheme {
                Scaffold(
                    topBar = {
                        AppBasicTopBar("Lectura con IA")
                    },
                    content = { innerPadding ->
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

                            Box(
                                modifier = Modifier
                                    .absoluteOffset()
                            ) {
                                if (bitmapImage == null) {
                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 40.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(50.dp)
                                                .clip(RoundedCornerShape(25.dp))
                                                .clickable(onClick = {
                                                    viewModel.takePhoto(
                                                        imageCapture = imageCapture!!,
                                                        executor = cameraExecutor!!,
                                                    )
                                                })
                                                .background(MaterialTheme.colorScheme.primary)
                                                .border(
                                                    2.dp,
                                                    MaterialTheme.colorScheme.surfaceDim,
                                                    RoundedCornerShape(25.dp)
                                                )
                                        )
                                    }
                                } else {
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceEvenly,
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 40.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(50.dp)
                                                .clickable(onClick = {
                                                    viewModel.resetBitmap()
                                                })
                                                .clip(RoundedCornerShape(10.dp))
                                        ) {
                                            Icon(
                                                painter = painterResource(R.drawable.close),
                                                contentDescription = "Captured Image",
                                                modifier = Modifier.size(50.dp),
                                                tint = MaterialTheme.colorScheme.error
                                            )
                                        }

                                        Box(
                                            modifier = Modifier
                                                .size(50.dp)
                                                .clickable(onClick = {})
                                                .clip(RoundedCornerShape(10.dp))
                                        ) {
                                            Icon(
                                                painter = painterResource(R.drawable.check),
                                                contentDescription = "Captured Image",
                                                modifier = Modifier.size(50.dp),
                                                tint = MaterialTheme.colorScheme.primary
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                )
            }
        }
    }

    @Composable
    private fun CameraPreview(
        modifier: Modifier = Modifier,
        cameraProviderFuture: ListenableFuture<ProcessCameraProvider>,
        imageCapture: ImageCapture,
    ) {
        val context = LocalContext.current
        val lifecycleOwner = LocalLifecycleOwner.current
        val previewView = remember { PreviewView(context) }

        AndroidView(
            factory = { previewView },
            modifier = modifier
        ) {
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }
                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        imageCapture
                    )
                } catch (exc: Exception) {
                    // Manejar errores al enlazar la cámara
                }
            }, ContextCompat.getMainExecutor(context))
        }
    }
}