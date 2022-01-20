package com.jj.templateproject.di.koin

import com.jj.templateproject.data.text.VersionTextProvider
import com.jj.templateproject.navigation.NoteNavigationImpl
import com.jj.templateproject.notes.navigation.NoteNavigation
import org.koin.dsl.module

val mainModule = module {
    single { VersionTextProvider() }

    single<NoteNavigation> { NoteNavigationImpl() }
}
