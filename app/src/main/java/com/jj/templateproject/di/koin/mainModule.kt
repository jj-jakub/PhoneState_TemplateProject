package com.jj.templateproject.di.koin

import com.jj.templateproject.data.coroutines.CoroutineScopeProvider
import com.jj.templateproject.data.coroutines.ICoroutineScopeProvider
import com.jj.templateproject.data.device.DeviceStateManager
import com.jj.templateproject.data.network.RetrofitFactory
import com.jj.templateproject.data.text.VersionTextProvider
import com.jj.templateproject.domain.airplanemode.AirplaneModeManager
import com.jj.templateproject.domain.device.IDeviceStateManager
import com.jj.templateproject.domain.network.NetworkManager
import com.jj.templateproject.framework.airplanemode.AirplaneModeListener
import com.jj.templateproject.framework.airplanemode.AndroidAirplaneModeManager
import com.jj.templateproject.framework.network.AndroidNetworkManager
import com.jj.templateproject.framework.network.NetworkStateListener
import org.koin.dsl.module

val mainModule = module {

    single { RetrofitFactory() }
    single <ICoroutineScopeProvider> { CoroutineScopeProvider() }
    single { NetworkStateListener() }
    single <NetworkManager> { AndroidNetworkManager(get(), get(), get()) }

    single { AirplaneModeListener() }
    single<AirplaneModeManager> { AndroidAirplaneModeManager(get(), get(), get()) }

    single<IDeviceStateManager> { DeviceStateManager(get(), get(), get()) }
}

val textModule = module {

    single { VersionTextProvider() }
}