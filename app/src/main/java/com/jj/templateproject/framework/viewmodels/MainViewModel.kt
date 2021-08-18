package com.jj.templateproject.framework.viewmodels

import androidx.lifecycle.ViewModel
import com.jj.templateproject.data.coroutines.ICoroutineScopeProvider
import com.jj.templateproject.data.device.DeviceState
import com.jj.templateproject.data.device.DeviceStateChange
import com.jj.templateproject.domain.airplanemode.AirplaneModeState
import com.jj.templateproject.domain.device.IDeviceStateManager
import com.jj.templateproject.domain.network.NetworkState
import com.jj.templateproject.domain.network.NetworkState.Connected
import com.jj.templateproject.domain.network.NetworkState.NotConnected
import com.jj.templateproject.domain.network.NetworkState.Unknown
import com.jj.templateproject.framework.viewmodels.states.AirplaneViewState
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

    private val deviceStateManager: IDeviceStateManager by inject(IDeviceStateManager::class.java)
    private val coroutineScopeProvider: ICoroutineScopeProvider by inject(ICoroutineScopeProvider::class.java)

    init {
        coroutineScopeProvider.createIOScope().launch {
            deviceStateManager.observeDeviceState().collect { onDeviceStateChanged(it) }
        }
    }

    private fun onDeviceStateChanged(newDeviceState: DeviceState) {
        val newMainViewState = when (newDeviceState.change) {
            DeviceStateChange.NETWORK -> mainViewStateFlow.value.copy(
                networkViewState = createNetworkViewState(newDeviceState.networkState)
            )
            DeviceStateChange.AIRPLANE -> mainViewStateFlow.value.copy(
                airplaneViewState = createAirplaneViewState(newDeviceState.airplaneModeState)
            )
            DeviceStateChange.NONE -> mainViewStateFlow.value
        }

        changeMainViewStateFlow(newMainViewState)
    }

    private fun createNetworkViewState(networkState: NetworkState) = when (networkState) {
        is Connected -> NetworkViewState(
            isKnown = true,
            isActive = true,
            type = networkState.type
        )
        is NotConnected -> NetworkViewState(isKnown = true, isActive = false)
        is Unknown -> NetworkViewState(isKnown = false)
    }

    private fun createAirplaneViewState(airplaneModeState: AirplaneModeState) =
        when (airplaneModeState) {
            is AirplaneModeState.TurnedOn -> AirplaneViewState(isKnown = true, isActive = true)
            is AirplaneModeState.TurnedOff -> AirplaneViewState(isKnown = true, isActive = false)
            is AirplaneModeState.Unknown -> AirplaneViewState(isKnown = false)
        }

    private fun changeMainViewStateFlow(mainViewState: MainViewState) {
        mainViewStateFlow.value = mainViewState
    }
}