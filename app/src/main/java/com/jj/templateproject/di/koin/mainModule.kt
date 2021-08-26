package com.jj.templateproject.di.koin

import com.jj.templateproject.data.coroutines.CoroutineScopeProvider
import com.jj.templateproject.data.coroutines.ICoroutineScopeProvider
import com.jj.templateproject.framework.device.AndroidDeviceStateManager
import com.jj.templateproject.data.network.RetrofitFactory
import com.jj.templateproject.data.text.VersionTextProvider
import com.jj.templateproject.domain.airplanemode.AirplaneModeManager
import com.jj.templateproject.domain.bluetooth.BluetoothStateManager
import com.jj.templateproject.domain.device.DeviceStateManager
import com.jj.templateproject.domain.network.NetworkManager
import com.jj.templateproject.framework.airplanemode.AirplaneModeListener
import com.jj.templateproject.framework.airplanemode.AndroidAirplaneModeManager
import com.jj.templateproject.framework.bluetooth.AndroidBluetoothStateManager
import com.jj.templateproject.framework.bluetooth.BluetoothStateListener
import com.jj.templateproject.framework.network.AndroidNetworkManager
import com.jj.templateproject.framework.network.NetworkStateListener
import org.koin.dsl.module

val mainModule = module {

    single { RetrofitFactory() }
    single<ICoroutineScopeProvider> { CoroutineScopeProvider() }
    single { NetworkStateListener() }
    single<NetworkManager> { AndroidNetworkManager(get(), get(), get()) }

    single { AirplaneModeListener() }
    single<AirplaneModeManager> { AndroidAirplaneModeManager(get(), get(), get()) }

    single { BluetoothStateListener() }
    single<BluetoothStateManager> { AndroidBluetoothStateManager(get(), get(), get()) }
    single<DeviceStateManager> { AndroidDeviceStateManager(get(), get(), get(), get()) }
}

val textModule = module {

    single { VersionTextProvider() }
}