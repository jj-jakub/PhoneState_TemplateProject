package com.jj.templateproject.framework.presentation

import android.app.Application
import com.jj.templateproject.di.koin.KoinLauncher
import com.jj.templateproject.di.koin.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TemplateProjectApplication : Application() {

    private val koinLauncher = KoinLauncher()

    override fun onCreate() {
        super.onCreate()
        koinLauncher.startKoin(this)
    }
}