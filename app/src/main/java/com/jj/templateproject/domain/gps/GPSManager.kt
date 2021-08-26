package com.jj.templateproject.domain.gps

import kotlinx.coroutines.flow.StateFlow

interface GPSManager {

    fun observeGPSState(): StateFlow<GPSState>
}