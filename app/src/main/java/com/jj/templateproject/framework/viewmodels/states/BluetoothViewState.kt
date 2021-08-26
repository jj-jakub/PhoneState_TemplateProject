package com.jj.templateproject.framework.viewmodels.states

import com.jj.templateproject.domain.bluetooth.BluetoothState

data class BluetoothViewState(
    val isKnown: Boolean = false,
    val isActive: Boolean = false,
    val bluetoothState: BluetoothState = BluetoothState.Unknown
)