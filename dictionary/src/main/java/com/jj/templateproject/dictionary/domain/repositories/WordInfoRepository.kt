package com.jj.templateproject.dictionary.domain.repositories

import com.jj.templateproject.core.domain.result.DataResult
import com.jj.templateproject.dictionary.domain.model.WordInfo
import kotlinx.coroutines.flow.Flow

interface WordInfoRepository {

    fun getWordInfo(word: String): Flow<DataResult<List<WordInfo>>>
}