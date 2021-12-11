package com.jj.templateproject.core.domain.device

sealed class DeviceStateChange {
    object Network: DeviceStateChange()
    object Airplane: DeviceStateChange()
    object Bluetooth: DeviceStateChange()
    object GPS: DeviceStateChange()
    object None: DeviceStateChange()
}
