package com.jj.templateproject.dictionary.data.remote

import com.jj.templateproject.dictionary.data.remote.dto.WordInfoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {

    @GET("/api/v2/entries/en/{word}")
    suspend fun getWordInfo(
            @Path("word") word: String
    ): List<WordInfoDto>
}