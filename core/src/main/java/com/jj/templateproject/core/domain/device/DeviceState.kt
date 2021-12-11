package com.jj.templateproject.core.domain.device

import com.jj.templateproject.core.domain.airplanemode.AirplaneModeState
import com.jj.templateproject.core.domain.bluetooth.BluetoothState
import com.jj.templateproject.core.domain.gps.GPSState
import com.jj.templateproject.core.domain.network.NetworkState

data class DeviceState(
        val networkState: NetworkState = NetworkState.Unknown,
        val airplaneModeState: AirplaneModeState = AirplaneModeState.Unknown,
        val bluetoothState: BluetoothState = BluetoothState.Unknown,
        val gpsState: GPSState = GPSState.Unknown,
        val change: DeviceStateChange = DeviceStateChange.None
)