package com.jj.templateproject.networking.di.koin

import com.jj.templateproject.core.data.network.RetrofitFactory
import com.jj.templateproject.networking.api.FishDataService
import com.jj.templateproject.networking.data.repositories.DefaultFishDataRepository
import com.jj.templateproject.networking.data.repositories.FishDataRepository
import com.jj.templateproject.networking.domain.API_URLS
import com.jj.templateproject.networking.domain.usecases.FilterSpeciesByNameUseCase
import com.jj.templateproject.networking.domain.usecases.FishResultsUseCases
import com.jj.templateproject.networking.domain.usecases.GetAllFishResultsUseCase
import com.jj.templateproject.networking.domain.usecases.GetTwoFishResultsUseCase
import com.jj.templateproject.networking.framework.viewmodels.ApiResultsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val networkingModule = module {
    single<FishDataService> { get<RetrofitFactory>().createService(API_URLS.BASE_API) }
    single<FishDataRepository> { DefaultFishDataRepository(get()) }

    viewModel { ApiResultsViewModel(get()) }

    /** Use Cases */
    single { FishResultsUseCases(get(), get(), get()) }
    single { GetAllFishResultsUseCase(get(), get()) }
    single { GetTwoFishResultsUseCase(get(), get()) }
    single { FilterSpeciesByNameUseCase() }
}