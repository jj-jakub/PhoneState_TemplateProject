package com.jj.templateproject.framework.viewmodels

import androidx.lifecycle.ViewModel
import com.jj.templateproject.data.coroutines.ICoroutineScopeProvider
import com.jj.templateproject.domain.airplanemode.AirplaneModeState
import com.jj.templateproject.domain.bluetooth.BluetoothState
import com.jj.templateproject.domain.device.DeviceState
import com.jj.templateproject.domain.device.DeviceStateChange
import com.jj.templateproject.domain.device.DeviceStateManager
import com.jj.templateproject.domain.gps.GPSState
import com.jj.templateproject.domain.network.NetworkState
import com.jj.templateproject.domain.network.NetworkState.Connected
import com.jj.templateproject.domain.network.NetworkState.NotConnected
import com.jj.templateproject.domain.network.NetworkState.Unknown
import com.jj.templateproject.framework.viewmodels.states.AirplaneModeViewState
import com.jj.templateproject.framework.viewmodels.states.BluetoothViewState
import com.jj.templateproject.framework.viewmodels.states.GPSViewState
import com.jj.templateproject.framework.viewmodels.states.MainViewState
import com.jj.templateproject.framework.viewmodels.states.NetworkViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class MainViewModel : ViewModel() {

    private val mainViewStateFlow = MutableStateFlow(MainViewState())

    fun observeMainViewState() = mainViewStateFlow.asStateFlow()

    private fun changeMainViewStateFlow(mainViewState: MainViewState) {
        mainViewStateFlow.value = mainViewState
    }
}