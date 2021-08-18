package com.jj.templateproject.framework.bluetooth

import android.bluetooth.BluetoothAdapter
import android.content.Context
import com.jj.templateproject.data.coroutines.ICoroutineScopeProvider
import com.jj.templateproject.domain.bluetooth.BluetoothModeChange
import com.jj.templateproject.domain.bluetooth.BluetoothModeManager
import com.jj.templateproject.domain.bluetooth.BluetoothModeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AndroidBluetoothModeManager(
    private val context: Context,
    private val bluetoothModeListener: BluetoothModeListener,
    private val coroutineScopeProvider: ICoroutineScopeProvider
) : BluetoothModeManager {

    private val bluetoothModeStateFlow =
        MutableStateFlow<BluetoothModeState>(BluetoothModeState.Unknown)

    override fun observeBluetoothState() = bluetoothModeStateFlow.asStateFlow()

    init {
        checkInitialBluetoothState()
        registerBluetoothCallback()
        observeBluetoothChanges()
    }

    private fun checkInitialBluetoothState() {
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        val bluetoothModeState = when {
            bluetoothAdapter == null -> BluetoothModeState.NotAvailable
            bluetoothAdapter.isEnabled -> BluetoothModeState.TurnedOn
            else -> BluetoothModeState.TurnedOff
        }

        changeBluetoothModeStateFlow(bluetoothModeState)
    }

    private fun registerBluetoothCallback() {
        context.registerReceiver(bluetoothModeListener, BluetoothModeListener.intentFilter)
    }

    private fun observeBluetoothChanges() {
        coroutineScopeProvider.createIOScope().launch {
            bluetoothModeListener.observeBluetoothModeChangeFlow().collect {
                onBluetoothModeChanged(it)
            }
        }
    }

    private fun onBluetoothModeChanged(bluetoothModeChange: BluetoothModeChange) {
        val bluetoothModeState = when (bluetoothModeChange) {
            is BluetoothModeChange.TurningOn -> BluetoothModeState.TurningOn
            is BluetoothModeChange.TurnedOn -> BluetoothModeState.TurnedOn
            is BluetoothModeChange.TurningOff -> BluetoothModeState.TurningOff
            is BluetoothModeChange.TurnedOff -> BluetoothModeState.TurnedOff
            else -> return
        }

        changeBluetoothModeStateFlow(bluetoothModeState)
    }

    private fun changeBluetoothModeStateFlow(bluetoothModeState: BluetoothModeState) {
        bluetoothModeStateFlow.value = bluetoothModeState
    }
}