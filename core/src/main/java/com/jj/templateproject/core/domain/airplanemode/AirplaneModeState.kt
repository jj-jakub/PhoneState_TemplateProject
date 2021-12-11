package com.jj.templateproject.core.domain.airplanemode

sealed class AirplaneModeState {
    object TurnedOn: AirplaneModeState()
    object TurnedOff: AirplaneModeState()
    object Unknown: AirplaneModeState()
}