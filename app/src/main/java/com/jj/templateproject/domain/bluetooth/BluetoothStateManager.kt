package com.jj.templateproject.domain.bluetooth

import kotlinx.coroutines.flow.StateFlow

interface BluetoothStateManager {

    fun observeBluetoothState(): StateFlow<BluetoothState>
}