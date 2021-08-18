package com.jj.templateproject.domain.airplanemode

sealed class AirplaneModeChange {
    object TurnedOn : AirplaneModeChange()
    object TurnedOff : AirplaneModeChange()
    object Unknown : AirplaneModeChange()
}