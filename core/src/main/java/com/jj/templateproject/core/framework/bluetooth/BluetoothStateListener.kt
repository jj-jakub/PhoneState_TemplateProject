package com.jj.templateproject.core.framework.bluetooth

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.jj.templateproject.core.domain.bluetooth.BluetoothChange
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class BluetoothStateListener : BroadcastReceiver() {

    companion object {
        val intentFilter = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
    }

    private val bluetoothChangeFlow =
        MutableStateFlow<BluetoothChange>(BluetoothChange.Unknown)

    fun observeBluetoothChangeFlow() = bluetoothChangeFlow.asStateFlow()

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == BluetoothAdapter.ACTION_STATE_CHANGED) {
            val bluetoothChange = getBluetoothChange(intent)
            changeBluetoothFlow(bluetoothChange)
        }
    }

    private fun getBluetoothChange(intent: Intent) =
        when (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1)) {
            BluetoothAdapter.STATE_TURNING_ON -> BluetoothChange.TurningOn
            BluetoothAdapter.STATE_ON -> BluetoothChange.TurnedOn
            BluetoothAdapter.STATE_TURNING_OFF -> BluetoothChange.TurningOff
            BluetoothAdapter.STATE_OFF -> BluetoothChange.TurnedOff
            else -> BluetoothChange.Unknown
        }

    private fun changeBluetoothFlow(bluetoothChange: BluetoothChange) {
        bluetoothChangeFlow.value = bluetoothChange
    }
}