package com.jj.templateproject.core.framework.presentation.viewmodels.states

data class MainViewState(
        val networkViewState: NetworkViewState = NetworkViewState(),
        val airplaneModeViewState: AirplaneModeViewState = AirplaneModeViewState(),
        val bluetoothViewState: BluetoothViewState = BluetoothViewState(),
        val gpsViewState: GPSViewState = GPSViewState()
) : BaseViewState