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

    private val deviceStateManager: DeviceStateManager by inject(DeviceStateManager::class.java)
    private val coroutineScopeProvider: ICoroutineScopeProvider by inject(ICoroutineScopeProvider::class.java)

    init {
        coroutineScopeProvider.createIOScope().launch {
            deviceStateManager.observeDeviceState().collect { onDeviceStateChanged(it) }
        }
    }

    private fun onDeviceStateChanged(newDeviceState: DeviceState) {
        val newMainViewState = when (newDeviceState.change) {
            DeviceStateChange.Network -> mainViewStateFlow.value.copy(
                networkViewState = createNetworkViewState(newDeviceState.networkState)
            )
            DeviceStateChange.Airplane -> mainViewStateFlow.value.copy(
                airplaneModeViewState = createAirplaneViewState(newDeviceState.airplaneModeState)
            )
            DeviceStateChange.Bluetooth -> mainViewStateFlow.value.copy(
                bluetoothViewState = createBluetoothViewState(newDeviceState.bluetoothState)
            )
            DeviceStateChange.GPS -> mainViewStateFlow.value.copy(
                gpsViewState = createGPSViewState(newDeviceState.gpsState)
            )
            DeviceStateChange.None -> mainViewStateFlow.value
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

    // TODO Maybe extract these creations
    private fun createBluetoothViewState(bluetoothState: BluetoothState) =
        when (bluetoothState) {
            is BluetoothState.TurnedOn -> BluetoothViewState(
                isKnown = true, isActive = true, bluetoothState = bluetoothState
            )
            is BluetoothState.TurningOn, BluetoothState.TurningOff, BluetoothState.TurnedOff,
            BluetoothState.NotAvailable -> BluetoothViewState(
                isKnown = true, isActive = false, bluetoothState = bluetoothState
            )
            else -> BluetoothViewState(isKnown = false, isActive = false)
        }

    private fun createGPSViewState(gpsState: GPSState) = when(gpsState) {
        is GPSState.TurnedOn -> GPSViewState(isKnown = true, isActive = true)
        is GPSState.TurnedOff -> GPSViewState(isKnown = true, isActive = false)
        is GPSState.Unknown -> GPSViewState(isKnown = false)
    }

    private fun changeMainViewStateFlow(mainViewState: MainViewState) {
        mainViewStateFlow.value = mainViewState
    }
}