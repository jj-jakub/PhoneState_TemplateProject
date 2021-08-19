package com.jj.templateproject.domain.device

sealed class DeviceStateChange {
    object Network: DeviceStateChange()
    object Airplane: DeviceStateChange()
    object Bluetooth: DeviceStateChange()
    object None: DeviceStateChange()
}
