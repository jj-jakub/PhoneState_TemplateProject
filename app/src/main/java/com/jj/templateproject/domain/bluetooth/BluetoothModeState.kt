package com.jj.templateproject.domain.bluetooth

sealed class BluetoothModeState {
    object TurningOn: BluetoothModeState()
    object TurnedOn: BluetoothModeState()
    object TurningOff: BluetoothModeState()
    object TurnedOff: BluetoothModeState()
    object NotAvailable: BluetoothModeState()
    object Unknown: BluetoothModeState()
}