package com.jj.templateproject.networking.data.repositories

import com.jj.templateproject.networking.api.FishDataService
import com.jj.templateproject.networking.domain.fishdata.FishDataResponseItem
import com.jj.templateproject.core.domain.result.DataResult
import com.jj.templateproject.core.domain.result.getDataResult

interface FishDataRepository {

    suspend fun fetchAllSpecies(): DataResult<List<FishDataResponseItem>>

    suspend fun fetchSpecifiedSpeciesInfo(name: String): DataResult<List<FishDataResponseItem>>
}

class DefaultFishDataRepository(private val fishDataService: FishDataService) : FishDataRepository {

    override suspend fun fetchAllSpecies() = getDataResult { fishDataService.getAllSpeciesInfo() }

    override suspend fun fetchSpecifiedSpeciesInfo(name: String) = getDataResult { fishDataService.getSpecifiedSpeciesInfo(name) }
}