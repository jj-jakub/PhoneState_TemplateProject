package com.jj.templateproject.core.framework.device

import com.jj.templateproject.core.domain.airplanemode.AirplaneModeManager
import com.jj.templateproject.core.domain.airplanemode.AirplaneModeState
import com.jj.templateproject.core.domain.bluetooth.BluetoothState
import com.jj.templateproject.core.domain.bluetooth.BluetoothStateManager
import com.jj.templateproject.core.domain.device.DeviceState
import com.jj.templateproject.core.domain.device.DeviceStateChange
import com.jj.templateproject.core.domain.device.DeviceStateManager
import com.jj.templateproject.core.domain.gps.GPSManager
import com.jj.templateproject.core.domain.gps.GPSState
import com.jj.templateproject.core.domain.network.NetworkManager
import com.jj.templateproject.core.domain.network.NetworkState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AndroidDeviceStateManager(
        networkManager: NetworkManager,
        airplaneModeManager: AirplaneModeManager,
        bluetoothStateManager: BluetoothStateManager,
        gpsManager: GPSManager,
        coroutineScopeProvider: com.jj.templateproject.core.data.coroutines.ICoroutineScopeProvider
) : DeviceStateManager {

    private val deviceStateFlow = MutableStateFlow(DeviceState())

    override fun observeDeviceState() = deviceStateFlow.asStateFlow()

    init {
        coroutineScopeProvider.createIOScope().launch {
            networkManager.observeNetworkState().collect { onNetworkStateChanged(it) }
        }

        coroutineScopeProvider.createIOScope().launch {
            airplaneModeManager.observeAirplaneModeState()
                .collect { onAirplaneModeStateChanged(it) }
        }

        coroutineScopeProvider.createIOScope().launch {
            bluetoothStateManager.observeBluetoothState().collect {
                onBluetoothStateChanged(it)
            }
        }

        coroutineScopeProvider.createIOScope().launch {
            gpsManager.observeGPSState().collect { onGPSStateChanged(it) }
        }
    }

    private fun onBluetoothStateChanged(bluetoothState: BluetoothState) {
        val newDeviceState = deviceStateFlow.value.copy(
            bluetoothState = bluetoothState,
            change = DeviceStateChange.Bluetooth
        )

        changeDeviceStateFlow(newDeviceState)
    }

    private fun onGPSStateChanged(gpsState: GPSState) {
        val newDeviceState = deviceStateFlow.value.copy(
            gpsState = gpsState,
            change = DeviceStateChange.GPS
        )

        changeDeviceStateFlow(newDeviceState)
    }

    private fun onNetworkStateChanged(networkState: NetworkState) {
        val newDeviceState = deviceStateFlow.value.copy(
            networkState = networkState,
            change = DeviceStateChange.Network
        )
        changeDeviceStateFlow(newDeviceState)
    }

    private fun onAirplaneModeStateChanged(airplaneModeState: AirplaneModeState) {
        val newDeviceState = deviceStateFlow.value.copy(
            airplaneModeState = airplaneModeState,
            change = DeviceStateChange.Airplane
        )
        changeDeviceStateFlow(newDeviceState)
    }

    private fun changeDeviceStateFlow(deviceState: DeviceState) {
        deviceStateFlow.value = deviceState
    }
}