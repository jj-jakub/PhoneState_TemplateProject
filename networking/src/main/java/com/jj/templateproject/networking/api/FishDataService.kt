package com.jj.templateproject.networking.api

import com.jj.templateproject.networking.domain.API_URLS
import com.jj.templateproject.networking.domain.fishdata.FishDataResponseItem
import retrofit2.http.GET
import retrofit2.http.Path

interface FishDataService {

    @GET("${API_URLS.FISH_API}/species")
    suspend fun getAllSpeciesInfo(): List<FishDataResponseItem>

    @GET("${API_URLS.FISH_API}/species/{name}")
    suspend fun getSpecifiedSpeciesInfo(@Path("name") name: String): List<FishDataResponseItem>
}