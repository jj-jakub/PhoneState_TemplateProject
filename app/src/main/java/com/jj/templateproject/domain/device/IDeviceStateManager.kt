package com.jj.templateproject.domain.device

import kotlinx.coroutines.flow.StateFlow

interface IDeviceStateManager {

    fun observeDeviceState(): StateFlow<DeviceState>
}