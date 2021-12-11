package com.jj.templateproject.core.di.koin

import com.jj.templateproject.core.domain.airplanemode.AirplaneModeManager
import com.jj.templateproject.core.domain.bluetooth.BluetoothStateManager
import com.jj.templateproject.core.domain.device.DeviceStateManager
import com.jj.templateproject.core.domain.gps.GPSManager
import com.jj.templateproject.core.domain.network.NetworkManager
import com.jj.templateproject.core.framework.airplanemode.AirplaneModeListener
import com.jj.templateproject.core.framework.airplanemode.AndroidAirplaneModeManager
import com.jj.templateproject.core.framework.bluetooth.AndroidBluetoothStateManager
import com.jj.templateproject.core.framework.bluetooth.BluetoothStateListener
import com.jj.templateproject.core.framework.device.AndroidDeviceStateManager
import com.jj.templateproject.core.framework.gps.AndroidGPSManager
import com.jj.templateproject.core.framework.gps.GPSStateListener
import com.jj.templateproject.core.framework.network.AndroidNetworkManager
import com.jj.templateproject.core.framework.network.NetworkStateListener
import org.koin.dsl.module

val coreModule = module {

    single { com.jj.templateproject.core.data.network.RetrofitFactory() }
    single<com.jj.templateproject.core.data.coroutines.ICoroutineScopeProvider> { com.jj.templateproject.core.data.coroutines.CoroutineScopeProvider() }
    single { NetworkStateListener() }
    single<NetworkManager> { AndroidNetworkManager(get(), get(), get()) }

    single { AirplaneModeListener() }
    single<AirplaneModeManager> { AndroidAirplaneModeManager(get(), get(), get()) }

    single { BluetoothStateListener() }
    single<BluetoothStateManager> { AndroidBluetoothStateManager(get(), get(), get()) }

    single { GPSStateListener(get()) }
    single<GPSManager> { AndroidGPSManager(get(), get(), get()) }
    single<DeviceStateManager> { AndroidDeviceStateManager(get(), get(), get(), get(), get()) }
}