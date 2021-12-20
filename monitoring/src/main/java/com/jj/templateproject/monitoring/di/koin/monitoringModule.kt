package com.jj.templateproject.monitoring.di.koin

import com.jj.templateproject.monitoring.data.server.server.HttpServerManager
import com.jj.templateproject.monitoring.data.server.server.ServerManager
import com.jj.templateproject.monitoring.framework.viewmodels.ServerStatusViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val monitoringModule = module {
    single<ServerManager> { HttpServerManager(get()) }

    viewModel { ServerStatusViewModel(get()) }
}