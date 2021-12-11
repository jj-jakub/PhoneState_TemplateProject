package com.jj.templateproject.core.domain.gps

sealed class GPSState {
    object TurnedOn: GPSState()
    object TurnedOff: GPSState()
    object Unknown: GPSState()
}