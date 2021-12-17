package com.jj.templateproject.utils.di

import com.jj.templateproject.core.di.koin.coreModule
import com.jj.templateproject.di.koin.mainModule
import com.jj.templateproject.networking.di.koin.networkingModule

val defaultKoinModules = listOf(mainModule, coreModule, networkingModule)