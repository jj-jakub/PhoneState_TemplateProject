package com.jj.templateproject.data.device

import com.jj.templateproject.data.coroutines.ICoroutineScopeProvider
import com.jj.templateproject.domain.airplanemode.AirplaneModeManager
import com.jj.templateproject.domain.airplanemode.AirplaneModeState
import com.jj.templateproject.domain.device.DeviceState
import com.jj.templateproject.domain.device.DeviceStateChange
import com.jj.templateproject.domain.device.IDeviceStateManager
import com.jj.templateproject.domain.network.NetworkManager
import com.jj.templateproject.domain.network.NetworkState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DeviceStateManager(
    networkManager: NetworkManager,
    airplaneModeManager: AirplaneModeManager,
    coroutineScopeProvider: ICoroutineScopeProvider
) : IDeviceStateManager {

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
    }

    private fun onNetworkStateChanged(networkState: NetworkState) {
        val newDeviceState = deviceStateFlow.value.copy(
            networkState = networkState,
            change = DeviceStateChange.NETWORK
        )
        changeDeviceStateFlow(newDeviceState)
    }

    private fun onAirplaneModeStateChanged(airplaneModeState: AirplaneModeState) {
        val newDeviceState = deviceStateFlow.value.copy(
            airplaneModeState = airplaneModeState,
            change = DeviceStateChange.AIRPLANE
        )
        changeDeviceStateFlow(newDeviceState)
    }

    private fun changeDeviceStateFlow(deviceState: DeviceState) {
        deviceStateFlow.value = deviceState
    }
}