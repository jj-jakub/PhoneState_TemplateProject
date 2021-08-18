package com.jj.templateproject.domain.device

import kotlinx.coroutines.flow.StateFlow

interface DeviceStateManager {

    fun observeDeviceState(): StateFlow<DeviceState>
}