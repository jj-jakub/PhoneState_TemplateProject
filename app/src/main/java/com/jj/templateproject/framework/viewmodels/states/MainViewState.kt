package com.jj.templateproject.framework.viewmodels.states

data class MainViewState(
    val networkViewState: NetworkViewState = NetworkViewState(),
    val airplaneModeViewState: AirplaneModeViewState = AirplaneModeViewState(),
    val bluetoothViewState: BluetoothViewState = BluetoothViewState()
) : BaseViewState