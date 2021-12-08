package com.jj.templateproject.framework.presentation.activities

import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.jj.templateproject.R
import com.jj.templateproject.data.text.VersionTextProvider
import com.jj.templateproject.databinding.ActivityMainBinding
import com.jj.templateproject.domain.bluetooth.BluetoothState
import com.jj.templateproject.framework.viewmodels.MainViewModel
import com.jj.templateproject.framework.viewmodels.states.AirplaneModeViewState
import com.jj.templateproject.framework.viewmodels.states.BluetoothViewState
import com.jj.templateproject.framework.viewmodels.states.GPSViewState
import com.jj.templateproject.framework.viewmodels.states.MainViewState
import com.jj.templateproject.framework.viewmodels.states.NetworkViewState
import kotlinx.coroutines.flow.collect
import org.koin.java.KoinJavaComponent.inject

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels()
    private val versionTextProvider: VersionTextProvider by inject(VersionTextProvider::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        setMainLabelText()
    }

    private fun setMainLabelText() {
        activityMainBinding.mainLabel.text = versionTextProvider.getAboutVersionText()
    }
}