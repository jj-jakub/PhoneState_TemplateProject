package com.jj.templateproject.domain.airplanemode

import kotlinx.coroutines.flow.StateFlow

interface AirplaneModeManager {

    fun observeAirplaneModeState(): StateFlow<AirplaneModeState>
}