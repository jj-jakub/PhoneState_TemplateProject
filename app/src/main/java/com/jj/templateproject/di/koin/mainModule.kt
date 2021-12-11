package com.jj.templateproject.di.koin

import com.jj.templateproject.data.text.VersionTextProvider
import org.koin.dsl.module

val mainModule = module {
    single { VersionTextProvider() }
}
