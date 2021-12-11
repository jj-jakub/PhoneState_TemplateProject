package com.jj.templateproject.core.domain.bluetooth

sealed class BluetoothChange {
    object TurningOn: BluetoothChange()
    object TurnedOn: BluetoothChange()
    object TurningOff: BluetoothChange()
    object TurnedOff: BluetoothChange()
    object Unknown: BluetoothChange()
}