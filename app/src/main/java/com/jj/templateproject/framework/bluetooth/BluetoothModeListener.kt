package com.jj.templateproject.framework.bluetooth

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.jj.templateproject.domain.bluetooth.BluetoothModeChange
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class BluetoothModeListener : BroadcastReceiver() {

    companion object {
        val intentFilter = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
    }

    // TODO Naming - bluetoothMode and bluetoothModeChange or bluetooth and bluetoothChange
    private val bluetoothModeChangeFlow =
        MutableStateFlow<BluetoothModeChange>(BluetoothModeChange.Unknown)

    fun observeBluetoothModeChangeFlow() = bluetoothModeChangeFlow.asStateFlow()

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == BluetoothAdapter.ACTION_STATE_CHANGED) {
            val bluetoothChange = getBluetoothChange(intent)
            changeBluetoothModeFlow(bluetoothChange)
        }
    }

    private fun getBluetoothChange(intent: Intent) =
        when (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1)) {
            BluetoothAdapter.STATE_TURNING_ON -> BluetoothModeChange.TurningOn
            BluetoothAdapter.STATE_ON -> BluetoothModeChange.TurnedOn
            BluetoothAdapter.STATE_TURNING_OFF -> BluetoothModeChange.TurningOff
            BluetoothAdapter.STATE_OFF -> BluetoothModeChange.TurnedOff
            else -> BluetoothModeChange.Unknown
        }

    private fun changeBluetoothModeFlow(bluetoothModeChange: BluetoothModeChange) {
        bluetoothModeChangeFlow.value = bluetoothModeChange
    }
}