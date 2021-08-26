package com.jj.templateproject.domain.gps

sealed class GPSChange {
    object TurnedOn: GPSChange()
    object TurnedOff: GPSChange()
    object Unknown: GPSChange()
}