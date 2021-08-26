package com.jj.templateproject.domain.gps

sealed class GPSState {
    object TurnedOn: GPSState()
    object TurnedOff: GPSState()
    object Unknown: GPSState()
}