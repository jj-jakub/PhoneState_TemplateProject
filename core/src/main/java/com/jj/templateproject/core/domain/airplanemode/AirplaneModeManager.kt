package com.jj.templateproject.core.domain.airplanemode

import kotlinx.coroutines.flow.StateFlow

interface AirplaneModeManager {

    fun observeAirplaneModeState(): StateFlow<AirplaneModeState>
}