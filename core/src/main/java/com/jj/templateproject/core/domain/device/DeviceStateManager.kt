package com.jj.templateproject.core.domain.device

import kotlinx.coroutines.flow.StateFlow

interface DeviceStateManager {

    fun observeDeviceState(): StateFlow<DeviceState>
}