package com.jj.templateproject.core.domain.airplanemode

sealed class AirplaneModeChange {
    object TurnedOn : AirplaneModeChange()
    object TurnedOff : AirplaneModeChange()
    object Unknown : AirplaneModeChange()
}