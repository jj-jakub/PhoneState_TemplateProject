package com.jj.templateproject.core.framework.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.jj.templateproject.core.domain.airplanemode.AirplaneModeState
import com.jj.templateproject.core.domain.bluetooth.BluetoothState
import com.jj.templateproject.core.domain.device.DeviceState
import com.jj.templateproject.core.domain.device.DeviceStateManager
import com.jj.templateproject.core.domain.gps.GPSState
import com.jj.templateproject.core.domain.network.NetworkState
import com.jj.templateproject.core.domain.network.NetworkState.Connected
import com.jj.templateproject.core.domain.network.NetworkState.NotConnected
import com.jj.templateproject.core.domain.network.NetworkState.Unknown
import com.jj.templateproject.core.framework.presentation.viewmodels.states.AirplaneModeViewState
import com.jj.templateproject.core.framework.presentation.viewmodels.states.BluetoothViewState
import com.jj.templateproject.core.framework.presentation.viewmodels.states.GPSViewState
import com.jj.templateproject.core.framework.presentation.viewmodels.states.MainViewState
import com.jj.templateproject.core.framework.presentation.viewmodels.states.NetworkViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent

class PhoneStateViewModel : BaseViewModel() {

    private val phoneStateViewStateFlow = MutableStateFlow(MainViewState())

    fun observePhoneStateViewState() = phoneStateViewStateFlow.asStateFlow()

    private val deviceStateManager: DeviceStateManager by KoinJavaComponent.inject(DeviceStateManager::class.java)

    init {
        viewModelScope.launch {
            deviceStateManager.observeDeviceState().collect { onDeviceStateChanged(it) }
        }
    }

    private fun onDeviceStateChanged(newDeviceState: DeviceState) {
        val newMainViewState = phoneStateViewStateFlow.value.copy(
            networkViewState = createNetworkViewState(newDeviceState.networkState),
            airplaneModeViewState = createAirplaneViewState(newDeviceState.airplaneModeState),
            bluetoothViewState = createBluetoothViewState(newDeviceState.bluetoothState),
            gpsViewState = createGPSViewState(newDeviceState.gpsState)
        )

        changePhoneStateViewStateFlow(newMainViewState)
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

    private fun createGPSViewState(gpsState: GPSState) = when (gpsState) {
        is GPSState.TurnedOn -> GPSViewState(isKnown = true, isActive = true)
        is GPSState.TurnedOff -> GPSViewState(isKnown = true, isActive = false)
        is GPSState.Unknown -> GPSViewState(isKnown = false)
    }

    private fun changePhoneStateViewStateFlow(mainViewState: MainViewState) {
        phoneStateViewStateFlow.value = mainViewState
    }
}