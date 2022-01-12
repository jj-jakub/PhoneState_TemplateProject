package com.jj.templateproject.dictionary.domain.usecases

import com.jj.templateproject.core.domain.result.DataResult.Error
import com.jj.templateproject.core.domain.result.DataResult.Loading
import com.jj.templateproject.core.domain.result.DataResult.Success
import com.jj.templateproject.core.domain.result.UseCaseResult
import com.jj.templateproject.dictionary.domain.model.WordInfo
import com.jj.templateproject.dictionary.domain.repositories.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GetWordInfoUseCase(private val wordInfoRepository: WordInfoRepository) {

    operator fun invoke(word: String): Flow<UseCaseResult<List<WordInfo>>> {
        if (word.isBlank()) {
            return flow { }
        }

        return wordInfoRepository.getWordInfo(word).map {
            val value = it.getValue() ?: emptyList()
            when (it) {
                is Success, is Loading -> UseCaseResult.Success(value)
                is Error -> UseCaseResult.Error(value, it.exception)
            }
        }
    }
}