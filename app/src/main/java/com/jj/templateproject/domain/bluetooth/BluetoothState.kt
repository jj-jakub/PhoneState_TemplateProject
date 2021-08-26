package com.jj.templateproject.domain.bluetooth

sealed class BluetoothState {
    object TurningOn: BluetoothState()
    object TurnedOn: BluetoothState()
    object TurningOff: BluetoothState()
    object TurnedOff: BluetoothState()
    object NotAvailable: BluetoothState()
    object Unknown: BluetoothState()
}