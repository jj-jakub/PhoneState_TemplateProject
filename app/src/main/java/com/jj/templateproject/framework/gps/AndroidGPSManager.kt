package com.jj.templateproject.framework.gps

import android.content.Context
import android.location.LocationManager
import com.jj.templateproject.data.coroutines.ICoroutineScopeProvider
import com.jj.templateproject.domain.gps.GPSChange
import com.jj.templateproject.domain.gps.GPSChange.TurnedOff
import com.jj.templateproject.domain.gps.GPSChange.TurnedOn
import com.jj.templateproject.domain.gps.GPSManager
import com.jj.templateproject.domain.gps.GPSState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AndroidGPSManager(
    private val context: Context,
    private val gpsStateListener: GPSStateListener,
    private val coroutineScopeProvider: ICoroutineScopeProvider
) : GPSManager {

    private var gpsStateFlow = MutableStateFlow<GPSState>(GPSState.Unknown)

    override fun observeGPSState(): StateFlow<GPSState> = gpsStateFlow.asStateFlow()


    init {
        checkInitialGPSState()
        registerGPSChangeListener()
        observeGPSChanges()
    }

    private fun observeGPSChanges() {
        coroutineScopeProvider.createIOScope().launch {
            gpsStateListener.observeGPSModeChangeFlow().collect { onGPSStateChanged(it) }
        }
    }

    private fun onGPSStateChanged(gpsChange: GPSChange) {
        val gpsState = when (gpsChange) {
            is TurnedOn -> GPSState.TurnedOn
            TurnedOff -> GPSState.TurnedOff
            else -> gpsStateFlow.value
        }

        changeGPSStateFlow(gpsState)
    }

    private fun checkInitialGPSState() {
        val manager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        val isGPSTurnedOn = manager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true
        changeGPSStateFlow(getGPSState(isGPSTurnedOn))
    }

    private fun registerGPSChangeListener() {
        context.registerReceiver(gpsStateListener, GPSStateListener.intentFilter)
    }

    private fun getGPSState(isGPSTurnedOn: Boolean) =
        if (isGPSTurnedOn) GPSState.TurnedOn
        else GPSState.TurnedOff

    private fun changeGPSStateFlow(gpsState: GPSState) {
        gpsStateFlow.value = gpsState
    }
}