package com.jj.templateproject.core.framework.airplanemode

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.jj.templateproject.core.domain.airplanemode.AirplaneModeChange
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AirplaneModeListener : BroadcastReceiver() {

    companion object {
        val intentFilter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
    }

    private val airplaneModeChangeFlow =
        MutableStateFlow<AirplaneModeChange>(AirplaneModeChange.Unknown)

    fun observeAirplaneModeChangeFlow() = airplaneModeChangeFlow.asStateFlow()

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
            val isTurnedOn = intent.getBooleanExtra("state", false)
            changeAirplaneModeFlow(isTurnedOn)
        }
    }

    private fun changeAirplaneModeFlow(turnedOn: Boolean) {
        val airplaneModeChange = if (turnedOn) AirplaneModeChange.TurnedOn
        else AirplaneModeChange.TurnedOff
        airplaneModeChangeFlow.value = airplaneModeChange
    }
}