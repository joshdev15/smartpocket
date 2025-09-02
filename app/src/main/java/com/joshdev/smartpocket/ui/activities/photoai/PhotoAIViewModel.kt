package com.joshdev.smartpocket.ui.activities.photoai

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.Log
import android.widget.Toast
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.google.common.util.concurrent.ListenableFuture
import java.util.concurrent.Executor

class PhotoAIViewModel : ViewModel() {
    private val activity = mutableStateOf<PhotoAIActivity?>(null)
    private val context = mutableStateOf<Context?>(null)
    private val invoiceId = mutableStateOf<Int?>(null)
    val bitmap = mutableStateOf<Bitmap?>(null)

    val cameraProviderFuture = mutableStateOf<ListenableFuture<ProcessCameraProvider>?>(null)
    val imageCapture = mutableStateOf<ImageCapture?>(null)
    val cameraExecutor = mutableStateOf<Executor?>(null)

    fun start(act: PhotoAIActivity, ctx: Context, invId: Int) {
        activity.value = act
        context.value = ctx
        invoiceId.value = invId

        cameraProviderFuture.value = ProcessCameraProvider.getInstance(ctx)
        imageCapture.value = ImageCapture.Builder().build()
        cameraExecutor.value = ContextCompat.getMainExecutor(ctx)
    }

    fun takePhoto(
        imageCapture: ImageCapture,
        executor: Executor,
    ) {
        imageCapture.takePicture(
            executor,
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    onPhotoCaptured(image)
                }

                override fun onError(exception: ImageCaptureException) {
                    onPhotoError(exception)
                }
            }
        )
    }

    private fun onPhotoCaptured(image: ImageProxy) {
        bitmap.value = image.toBitmapRotated()
    }

    private fun onPhotoError(error: ImageCaptureException) {
        Log.e("--> PhotoAI", "Error al tomar la foto", error)
        Toast.makeText(
            context.value,
            "Se ha producido un error al tomar la foto",
            Toast.LENGTH_SHORT
        ).show()
    }

    fun resetBitmap() {
        bitmap.value = null
    }

    private fun ImageProxy.toBitmapRotated(): Bitmap {
        val buffer = planes[0].buffer
        val bytes = ByteArray(buffer.remaining())
        buffer.get(bytes)
        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        val rotationDegrees = imageInfo.rotationDegrees.toFloat()

        if (rotationDegrees != 0f) {
            val matrix = Matrix().apply { postRotate(rotationDegrees) }
            val rotatedBitmap = Bitmap.createBitmap(
                bitmap,
                0,
                0,
                bitmap.width,
                bitmap.height,
                matrix,
                true
            )

            bitmap.recycle()
            return rotatedBitmap
        }

        return bitmap
    }
}