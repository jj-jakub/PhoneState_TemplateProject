package com.jj.templateproject.networking.data.repositories

import com.jj.templateproject.networking.api.FishDataService
import com.jj.templateproject.networking.domain.fishdata.FishDataResponseItem
import com.jj.templateproject.networking.domain.result.BaseResult

interface FishDataRepository {

    suspend fun fetchAllSpecies(): BaseResult<List<FishDataResponseItem>>
}

class DefaultFishDataRepository(private val fishDataService: FishDataService) : FishDataRepository {

    override suspend fun fetchAllSpecies() = BaseResult(fishDataService.getAllSpeciesInfo())
}