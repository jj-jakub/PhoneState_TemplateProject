package com.jj.templateproject.networking.api

import com.jj.templateproject.networking.domain.API_URLS
import com.jj.templateproject.networking.domain.fishdata.FishDataResponseItem
import retrofit2.http.GET

interface FishDataService {

    @GET("${API_URLS.FISH_API}/species")
    suspend fun getAllSpeciesInfo(): List<FishDataResponseItem>

//    fun getSpecifiedSpeciesInfo(name: String): FishDataResponse
}