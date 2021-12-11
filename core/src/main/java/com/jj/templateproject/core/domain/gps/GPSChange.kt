package com.jj.templateproject.core.domain.gps

sealed class GPSChange {
    object TurnedOn: GPSChange()
    object TurnedOff: GPSChange()
    object Unknown: GPSChange()
}