package com.jj.templateproject.domain.bluetooth

sealed class BluetoothModeChange {
    object TurningOn: BluetoothModeChange()
    object TurnedOn: BluetoothModeChange()
    object TurningOff: BluetoothModeChange()
    object TurnedOff: BluetoothModeChange()
    object Unknown: BluetoothModeChange()
}