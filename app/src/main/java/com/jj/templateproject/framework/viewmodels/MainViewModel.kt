package com.jj.templateproject.framework.viewmodels

import androidx.lifecycle.ViewModel
import com.jj.templateproject.data.coroutines.ICoroutineScopeProvider
import com.jj.templateproject.domain.airplanemode.AirplaneModeState
import com.jj.templateproject.domain.bluetooth.BluetoothModeState
import com.jj.templateproject.domain.device.DeviceState
import com.jj.templateproject.domain.device.DeviceStateChange
import com.jj.templateproject.domain.device.DeviceStateManager
import com.jj.templateproject.domain.network.NetworkState
import com.jj.templateproject.domain.network.NetworkState.Connected
import com.jj.templateproject.domain.network.NetworkState.NotConnected
import com.jj.templateproject.domain.network.NetworkState.Unknown
import com.jj.templateproject.framework.viewmodels.states.AirplaneModeViewState
import com.jj.templateproject.framework.viewmodels.states.BluetoothViewState
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

    private val deviceStateManager: DeviceStateManager by inject(DeviceStateManager::class.java)
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
                airplaneModeViewState = createAirplaneViewState(newDeviceState.airplaneModeState)
            )
            DeviceStateChange.BLUETOOTH -> mainViewStateFlow.value.copy(
                bluetoothViewState = createBluetoothViewState(newDeviceState.bluetoothState)
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
            is AirplaneModeState.TurnedOn -> AirplaneModeViewState(isKnown = true, isActive = true)
            is AirplaneModeState.TurnedOff -> AirplaneModeViewState(
                isKnown = true,
                isActive = false
            )
            is AirplaneModeState.Unknown -> AirplaneModeViewState(isKnown = false)
        }

    private fun createBluetoothViewState(bluetoothModeState: BluetoothModeState) =
        when (bluetoothModeState) {
            is BluetoothModeState.TurningOn -> BluetoothViewState(
                isKnown = true, isActive = false, type = "Turning on"
            )
            is BluetoothModeState.TurnedOn -> BluetoothViewState(
                isKnown = true, isActive = true, type = "Turned on"
            )
            is BluetoothModeState.TurningOff -> BluetoothViewState(
                isKnown = true, isActive = false, type = "Turning off"
            )
            is BluetoothModeState.TurnedOff -> BluetoothViewState(
                isKnown = true, isActive = false, type = "Turned off"
            )
            is BluetoothModeState.NotAvailable -> BluetoothViewState(
                isKnown = true, isActive = false, type = "Not available"
            )
            else -> BluetoothViewState(isKnown = false, isActive = false)
        }

    private fun changeMainViewStateFlow(mainViewState: MainViewState) {
        mainViewStateFlow.value = mainViewState
    }
}