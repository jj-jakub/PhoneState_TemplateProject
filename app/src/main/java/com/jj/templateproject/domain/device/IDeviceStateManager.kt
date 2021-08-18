package com.jj.templateproject.domain.device

import com.jj.templateproject.data.device.DeviceState
import kotlinx.coroutines.flow.StateFlow

interface IDeviceStateManager {

    fun observeDeviceState(): StateFlow<DeviceState>
}