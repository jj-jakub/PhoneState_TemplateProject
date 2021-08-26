package com.jj.templateproject.framework.gps

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import com.jj.templateproject.domain.gps.GPSChange
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GPSStateListener(private val context: Context) : BroadcastReceiver() {

    companion object {
        val intentFilter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
    }

    private val gpsModeChangeFlow = MutableStateFlow<GPSChange>(GPSChange.Unknown)

    fun observeGPSModeChangeFlow(): StateFlow<GPSChange> = gpsModeChangeFlow.asStateFlow()

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == LocationManager.PROVIDERS_CHANGED_ACTION) {
            onGPSProvidersChanged()
        }
    }

    private fun onGPSProvidersChanged() {
        val gpsChange = if (isGPSTurnedOn()) GPSChange.TurnedOn
        else GPSChange.TurnedOff
        gpsModeChangeFlow.value = gpsChange
    }

    private fun isGPSTurnedOn(): Boolean {
        val manager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        return manager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true
    }
}