package com.jj.templateproject.di.koin

import com.jj.templateproject.data.coroutines.CoroutineScopeProvider
import com.jj.templateproject.data.coroutines.ICoroutineScopeProvider
import com.jj.templateproject.data.network.RetrofitFactory
import com.jj.templateproject.data.text.VersionTextProvider
import com.jj.templateproject.domain.network.NetworkManager
import com.jj.templateproject.framework.network.AndroidNetworkManager
import com.jj.templateproject.framework.network.NetworkStateListener
import org.koin.dsl.module

val mainModule = module {

    single { RetrofitFactory() }
    single <ICoroutineScopeProvider> { CoroutineScopeProvider() }
    single { NetworkStateListener() }
    single <NetworkManager> { AndroidNetworkManager(get(), get(), get()) }
}

val textModule = module {

    single { VersionTextProvider() }
}