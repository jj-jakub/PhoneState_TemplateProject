package com.jj.templateproject.domain.device

import com.jj.templateproject.domain.airplanemode.AirplaneModeState
import com.jj.templateproject.domain.bluetooth.BluetoothModeState
import com.jj.templateproject.domain.network.NetworkState

data class DeviceState(
    val networkState: NetworkState = NetworkState.Unknown,
    val airplaneModeState: AirplaneModeState = AirplaneModeState.Unknown,
    val bluetoothState: BluetoothModeState = BluetoothModeState.Unknown,
    val change: DeviceStateChange = DeviceStateChange.None
)