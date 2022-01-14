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

class QrCodeAnalyzer(private val onQrCodeScanned: (String) -> Unit) : ImageAnalysis.Analyzer {

    private val supportedImageFormats = listOf(
            ImageFormat.YUV_420_888,
            ImageFormat.YUV_422_888,
            ImageFormat.YUV_444_888
    )

    private val options = BarcodeScannerOptions.Builder().setBarcodeFormats(Barcode.FORMAT_QR_CODE).build()

    private val scanner = BarcodeScanning.getClient(options)

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(image: ImageProxy) {
        if (image.format in supportedImageFormats && image.image != null) {
            val qrImage = InputImage.fromMediaImage(image.image, image.imageInfo.rotationDegrees)

            scanner.process(qrImage).addOnSuccessListener {
                if (it.isNotEmpty()) {
                    val qrInfo = it.first().rawValue
                    onQrCodeScanned(qrInfo)
                }
                image.close()
            }.addOnFailureListener {
                Log.e(QrCodeAnalyzer::class.java.simpleName, "Scanning error", it)
                image.close()
            }
        }
    }
}