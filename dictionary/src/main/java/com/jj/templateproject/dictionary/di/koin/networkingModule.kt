package com.jj.templateproject.dictionary.di.koin

import com.jj.templateproject.dictionary.framework.viewmodels.DictionaryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dictionaryModule = module {
    viewModel { DictionaryViewModel() }
}