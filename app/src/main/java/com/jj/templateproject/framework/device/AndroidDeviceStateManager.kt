package com.jj.templateproject.framework.device

import com.jj.templateproject.data.coroutines.ICoroutineScopeProvider
import com.jj.templateproject.domain.airplanemode.AirplaneModeManager
import com.jj.templateproject.domain.airplanemode.AirplaneModeState
import com.jj.templateproject.domain.bluetooth.BluetoothState
import com.jj.templateproject.domain.bluetooth.BluetoothStateManager
import com.jj.templateproject.domain.device.DeviceState
import com.jj.templateproject.domain.device.DeviceStateChange
import com.jj.templateproject.domain.device.DeviceStateManager
import com.jj.templateproject.domain.gps.GPSManager
import com.jj.templateproject.domain.gps.GPSState
import com.jj.templateproject.domain.network.NetworkManager
import com.jj.templateproject.domain.network.NetworkState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AndroidDeviceStateManager(
    networkManager: NetworkManager,
    airplaneModeManager: AirplaneModeManager,
    bluetoothStateManager: BluetoothStateManager,
    gpsManager: GPSManager,
    coroutineScopeProvider: ICoroutineScopeProvider
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