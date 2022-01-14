package com.jj.templateproject.qrscanner.framework.presentation

import android.os.Bundle
import androidx.activity.compose.setContent

class QrScannerActivity : BaseComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QrCodeScannerComposeTheme {

            }
        }
    }
}