package com.jj.templateproject.core.framework.bluetooth

import android.bluetooth.BluetoothAdapter
import android.content.Context
import com.jj.templateproject.core.data.coroutines.ICoroutineScopeProvider
import com.jj.templateproject.core.domain.bluetooth.BluetoothChange
import com.jj.templateproject.core.domain.bluetooth.BluetoothState
import com.jj.templateproject.core.domain.bluetooth.BluetoothStateManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AndroidBluetoothStateManager(
        private val context: Context,
        private val bluetoothStateListener: BluetoothStateListener,
        private val coroutineScopeProvider: ICoroutineScopeProvider
) : BluetoothStateManager {

    private val bluetoothStateFlow =
        MutableStateFlow<BluetoothState>(BluetoothState.Unknown)

    override fun observeBluetoothState() = bluetoothStateFlow.asStateFlow()

    init {
        checkInitialBluetoothState()
        registerBluetoothCallback()
        observeBluetoothChanges()
    }

    private fun checkInitialBluetoothState() {
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        val bluetoothState = when {
            bluetoothAdapter == null -> BluetoothState.NotAvailable
            bluetoothAdapter.isEnabled -> BluetoothState.TurnedOn
            else -> BluetoothState.TurnedOff
        }

        changeBluetoothStateFlow(bluetoothState)
    }

    private fun registerBluetoothCallback() {
        context.registerReceiver(bluetoothStateListener, BluetoothStateListener.intentFilter)
    }

    private fun observeBluetoothChanges() {
        coroutineScopeProvider.createIOScope().launch {
            bluetoothStateListener.observeBluetoothChangeFlow().collect {
                onBluetoothStateChanged(it)
            }
        }
    }

    private fun onBluetoothStateChanged(bluetoothChange: BluetoothChange) {
        val bluetoothState = when (bluetoothChange) {
            is BluetoothChange.TurningOn -> BluetoothState.TurningOn
            is BluetoothChange.TurnedOn -> BluetoothState.TurnedOn
            is BluetoothChange.TurningOff -> BluetoothState.TurningOff
            is BluetoothChange.TurnedOff -> BluetoothState.TurnedOff
            else -> return
        }

        changeBluetoothStateFlow(bluetoothState)
    }

    private fun changeBluetoothStateFlow(bluetoothState: BluetoothState) {
        bluetoothStateFlow.value = bluetoothState
    }
}