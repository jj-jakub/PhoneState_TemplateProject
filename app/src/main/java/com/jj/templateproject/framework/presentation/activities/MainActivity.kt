package com.jj.templateproject.framework.presentation.activities

import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.jj.templateproject.data.text.VersionTextProvider
import com.jj.templateproject.databinding.ActivityMainBinding
import com.jj.templateproject.framework.viewmodels.MainViewModel
import com.jj.templateproject.framework.viewmodels.states.AirplaneModeViewState
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

        lifecycleScope.launchWhenStarted {
            mainViewModel.observeMainViewState().collect {
                onMainViewStateChanged(it)
            }
        }
    }

    private fun onMainViewStateChanged(mainViewState: MainViewState) {
        setupNetworkViews(mainViewState.networkViewState)
        setupAirplaneModeViews(mainViewState.airplaneModeViewState)
    }

    private fun setupAirplaneModeViews(airplaneModeViewState: AirplaneModeViewState) {
        activityMainBinding.apply {
            airplaneModeStateIcon.setBackgroundColor(
                getBackgroundColor(
                    airplaneModeViewState.isKnown,
                    airplaneModeViewState.isActive
                )
            )
            airplaneModeStateValue.text = getValueText(
                airplaneModeViewState.isKnown,
                airplaneModeViewState.isActive
            )
        }
    }

    private fun setupNetworkViews(networkViewState: NetworkViewState) {
        activityMainBinding.apply {
            networkStateIcon.setBackgroundColor(
                getBackgroundColor(
                    networkViewState.isKnown,
                    networkViewState.isActive
                )
            )
            networkStateValue.text = getValueText(
                networkViewState.isKnown,
                networkViewState.isActive,
                networkViewState.type
            )
        }
    }

    private fun setMainLabelText() {
        activityMainBinding.mainLabel.text = versionTextProvider.getAboutVersionText()
    }

    private fun getBackgroundColor(isKnown: Boolean, active: Boolean): Int {
        return if (!isKnown) Color.GRAY
        else if (active) Color.GREEN else Color.RED
    }

    private fun getValueText(isKnown: Boolean, isActive: Boolean, type: String = ""): String {
        return if (!isKnown) "Unknown"
        else if (!isActive) "Not active"
        else if (type.isEmpty()) "Active" else type
    }
}