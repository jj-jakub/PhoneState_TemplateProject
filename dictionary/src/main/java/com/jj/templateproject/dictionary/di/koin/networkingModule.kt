package com.jj.templateproject.dictionary.di.koin

import androidx.room.Room
import com.google.gson.Gson
import com.jj.templateproject.core.data.network.RetrofitFactory
import com.jj.templateproject.dictionary.data.local.WordInfoDatabase
import com.jj.templateproject.dictionary.data.remote.API_CONST
import com.jj.templateproject.dictionary.data.repositories.DefaultWordInfoRepository
import com.jj.templateproject.dictionary.data.util.GsonParser
import com.jj.templateproject.dictionary.domain.repositories.WordInfoRepository
import com.jj.templateproject.dictionary.domain.usecases.GetWordInfoUseCase
import com.jj.templateproject.dictionary.framework.viewmodels.DictionaryViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dictionaryModule = module {
    single {
        Room.databaseBuilder(androidContext(), WordInfoDatabase::class.java, "word_database").addTypeConverter(GsonParser(Gson())).build()
    }
    single<WordInfoRepository> {
        DefaultWordInfoRepository(get<RetrofitFactory>().createService(API_CONST.WORD_BASE_URL), get<WordInfoDatabase>().dao)
    }
    single { GetWordInfoUseCase(get()) }
    viewModel { DictionaryViewModel(get()) }
}