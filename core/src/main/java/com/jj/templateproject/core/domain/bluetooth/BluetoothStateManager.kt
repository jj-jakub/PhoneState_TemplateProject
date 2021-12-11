package com.jj.templateproject.core.domain.bluetooth

import kotlinx.coroutines.flow.StateFlow

interface BluetoothStateManager {

    fun observeBluetoothState(): StateFlow<BluetoothState>
}