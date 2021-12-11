package com.jj.templateproject.core.domain.gps

import kotlinx.coroutines.flow.StateFlow

interface GPSManager {

    fun observeGPSState(): StateFlow<GPSState>
}