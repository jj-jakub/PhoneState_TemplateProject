package com.jj.templateproject.qrscanner.data

import android.annotation.SuppressLint
import android.graphics.ImageFormat
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class QrCodeAnalyzer(private val onQrCodeScanned: (String) -> Unit) : ImageAnalysis.Analyzer {

    private val supportedImageFormats = listOf(
            ImageFormat.YUV_420_888,
            ImageFormat.YUV_422_888,
            ImageFormat.YUV_444_888
    )

    private val options = BarcodeScannerOptions.Builder().setBarcodeFormats(Barcode.FORMAT_QR_CODE).build()

    private val scanner = BarcodeScanning.getClient(options)
    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(image: ImageProxy) {
        if (image.format in supportedImageFormats && image.image != null) {
            val inputImage = InputImage.fromMediaImage(image.image, image.imageInfo.rotationDegrees)

            recognizer.process(inputImage).addOnSuccessListener {
//                if (it.isNotEmpty()) {
//                    val qrInfo = it.first().rawValue
//                    Log.d("ABAB", "discovered: $qrInfo")
//                    onQrCodeScanned(qrInfo)
//                }
                Log.d("ABAB", "discovered: ${it.text}")
                onQrCodeScanned(it.text)
                image.close()
            }.addOnFailureListener {
                Log.e(QrCodeAnalyzer::class.java.simpleName, "Scanning error", it)
                image.close()
            }
        }
    }
}