package com.joshdev.smartpocket.ui.activities.photoai

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.Point
import android.hardware.camera2.CameraManager
import android.util.Log
import android.widget.Toast
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.joshdev.smartpocket.ui.models.AnalysedBlock
import java.util.concurrent.Executor
import androidx.core.content.ContextCompat.getSystemService

class PhotoAIViewModel : ViewModel() {
    val activity = mutableStateOf<PhotoAIActivity?>(null)
    val context = mutableStateOf<Context?>(null)
    private val invoiceId = mutableStateOf<Int?>(null)
    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    val bitmap = mutableStateOf<Bitmap?>(null)
    val result = mutableStateOf<List<AnalysedBlock?>>(emptyList())
    val cameraId = mutableStateOf<String?>(null)

    val cameraProviderFuture = mutableStateOf<ListenableFuture<ProcessCameraProvider>?>(null)
    val imageCapture = mutableStateOf<ImageCapture?>(null)
    val cameraExecutor = mutableStateOf<Executor?>(null)
    val navController = mutableStateOf<NavController?>(null)

    fun start(act: PhotoAIActivity, ctx: Context, invId: Int, nav: NavController) {
        activity.value = act
        context.value = ctx
        invoiceId.value = invId
        navController.value = nav

        cameraProviderFuture.value = ProcessCameraProvider.getInstance(ctx)
        imageCapture.value = ImageCapture.Builder().build()
        cameraExecutor.value = ContextCompat.getMainExecutor(ctx)

        getSystemService(ctx, CameraManager::class.java)?.let {
            cameraId.value = it.cameraIdList[0]
        }
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

    fun setResultToEdit(orders: List<Int>) {
        val tmpResult = result.value.filter { orders.contains(it?.order) }
        result.value = tmpResult
    }

    fun goToAnalysis() {
        navController.value?.navigate("analysis")
    }

    fun goToEdition() {
        navController.value?.navigate("edition")
    }

    fun analyzeImage() {
        bitmap.value?.let { img ->
            val imageBitmap = InputImage.fromBitmap(img, 0)
            recognizer.process(imageBitmap)
                .addOnSuccessListener { visionText ->
                    result.value = processText(visionText)
                }
                .addOnFailureListener { e -> showVisionError(e) }
        } ?: run { showVisionError() }
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

    private fun showVisionError(e: Exception? = null) {
        Toast.makeText(
            context.value,
            "No se puede reconocer el texto en la imagen" + (e?.message ?: ""),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun processText(result: Text): List<AnalysedBlock> {
        val allLines = mutableListOf<Text.Line>()
        for (block in result.textBlocks) {
            allLines.addAll(block.lines)
        }

        allLines.sortWith(compareBy { it.boundingBox?.top ?: 0 })

        val startIndex = allLines.indexOfFirst {
            it.text.lowercase().contains("factura")
        }
        val endIndex = allLines.indexOfFirst {
            it.text.lowercase().contains("total")
        }

        if (startIndex == -1 || endIndex == -1 || startIndex >= endIndex) {
            return emptyList()
        }

        val relevantLines = allLines.subList(startIndex, endIndex + 1)

        val analysedImage = mutableListOf<AnalysedBlock>()
        val processedLineIndices = mutableSetOf<Int>()

        relevantLines.forEachIndexed { index, line ->
            if (index in processedLineIndices) {
                return@forEachIndexed
            }

            val combinedLineText = StringBuilder()
            val combinedCornerPoints = mutableListOf<Point>()
            val combinedElements = mutableListOf<AnalysedBlock.AnalysedLine.AnalysedText>()

            combinedLineText.append(line.text)
            line.cornerPoints?.let { combinedCornerPoints.addAll(it) }
            line.elements.forEach { element ->
                combinedElements.add(AnalysedBlock.AnalysedLine.AnalysedText(element.text))
            }
            processedLineIndices.add(index)

            for (i in (index + 1) until allLines.size) {
                val nextLine = allLines[i]
                val verticalTolerance = 15

                if (Math.abs(line.boundingBox!!.centerY() - nextLine.boundingBox!!.centerY()) < verticalTolerance) {
                    combinedLineText.append(" ${nextLine.text}") // Unir el texto.
                    nextLine.cornerPoints?.let { combinedCornerPoints.addAll(it) } // Unir los puntos.
                    nextLine.elements.forEach { element ->
                        combinedElements.add(AnalysedBlock.AnalysedLine.AnalysedText(element.text))
                    }
                    processedLineIndices.add(i)
                } else {
                    break
                }
            }

            val newAnalysedLine = AnalysedBlock.AnalysedLine(
                line = combinedLineText.toString(),
                language = line.recognizedLanguage,
                cornerPoints = combinedCornerPoints.toTypedArray(),
                texts = combinedElements
            )

            analysedImage.add(
                AnalysedBlock(
                    block = newAnalysedLine.line,
                    language = newAnalysedLine.language,
                    cornerPoints = newAnalysedLine.cornerPoints,
                    lines = listOf(newAnalysedLine),
                    order = analysedImage.size
                )
            )
        }

        return analysedImage
    }

    fun toggleFlashlight(cameraManager: CameraManager, isOn: Boolean) {
        try {
            cameraId.value?.let {
                cameraManager.setTorchMode(it, isOn)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}