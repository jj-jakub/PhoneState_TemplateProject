package com.jj.templateproject.framework.airplanemode

import android.content.Context
import android.provider.Settings
import com.jj.templateproject.data.coroutines.ICoroutineScopeProvider
import com.jj.templateproject.domain.airplanemode.AirplaneModeManager
import com.jj.templateproject.domain.airplanemode.AirplaneModeState
import com.jj.templateproject.framework.airplanemode.AirplaneModeChange.TurnedOff
import com.jj.templateproject.framework.airplanemode.AirplaneModeChange.TurnedOn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AndroidAirplaneModeManager(
    private val context: Context,
    private val airplaneModeListener: AirplaneModeListener,
    private val coroutineScopeProvider: ICoroutineScopeProvider
) : AirplaneModeManager {

    private val airplaneModeStateFlow =
        MutableStateFlow<AirplaneModeState>(AirplaneModeState.Unknown)

    override fun observeAirplaneModeState() = airplaneModeStateFlow.asStateFlow()

    init {
        checkInitialAirplaneModeState()
        registerAirplaneModeReceiver()
        observeAirplaneModeChanges()
    }

    private fun checkInitialAirplaneModeState() {
        val airplaneModeState = getAirplaneModeState(isAirplaneModeOn())
        changeAirplaneModeStateFlow(airplaneModeState)
    }

    private fun registerAirplaneModeReceiver() {
        context.registerReceiver(airplaneModeListener, AirplaneModeListener.intentFilter)
    }

    private fun observeAirplaneModeChanges() {
        coroutineScopeProvider.createIOScope().launch {
            airplaneModeListener.observeAirplaneModeChangeFlow().collect {
                onAirplaneModeChanged(it)
            }
        }
    }

    private fun onAirplaneModeChanged(airplaneModeChange: AirplaneModeChange) {
        val airplaneModeState = when(airplaneModeChange) {
            is TurnedOn ->  AirplaneModeState.TurnedOn
            is TurnedOff -> AirplaneModeState.TurnedOff
            else -> return
        }

        changeAirplaneModeStateFlow(airplaneModeState)
    }

    private fun changeAirplaneModeStateFlow(airplaneModeState: AirplaneModeState) {
        airplaneModeStateFlow.value = airplaneModeState
    }

    private fun getAirplaneModeState(turnedOn: Boolean) =
        if (turnedOn) AirplaneModeState.TurnedOn
        else AirplaneModeState.TurnedOff

    private fun isAirplaneModeOn() = Settings.Global.getInt(
        context.contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0
    ) != 0
}