package com.jj.templateproject.domain.airplanemode

sealed class AirplaneModeState {
    object TurnedOn: AirplaneModeState()
    object TurnedOff: AirplaneModeState()
    object Unknown: AirplaneModeState()
}