package com.jj.templateproject.domain.bluetooth

import kotlinx.coroutines.flow.StateFlow

interface BluetoothModeManager {

    fun observeBluetoothState(): StateFlow<BluetoothModeState>
}