package com.jj.templateproject.core.framework.presentation.viewmodels.states

import com.jj.templateproject.core.domain.bluetooth.BluetoothState

data class BluetoothViewState(
        val isKnown: Boolean = false,
        val isActive: Boolean = false,
        val bluetoothState: BluetoothState = BluetoothState.Unknown
)