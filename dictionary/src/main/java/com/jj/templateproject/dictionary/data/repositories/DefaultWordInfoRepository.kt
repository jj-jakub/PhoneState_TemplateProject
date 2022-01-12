package com.jj.templateproject.dictionary.data.repositories

import com.jj.templateproject.core.domain.result.DataResult
import com.jj.templateproject.dictionary.data.local.WordInfoDao
import com.jj.templateproject.dictionary.data.remote.DictionaryApi
import com.jj.templateproject.dictionary.domain.model.WordInfo
import com.jj.templateproject.dictionary.domain.repositories.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultWordInfoRepository(
        private val dictionaryApi: DictionaryApi,
        private val wordInfoDao: WordInfoDao
) : WordInfoRepository {

    override fun getWordInfo(word: String): Flow<DataResult<List<WordInfo>>> = flow {
        emit(DataResult.Loading())

        val wordInfos = wordInfoDao.getWordInfos(word).map { it.toWordInfo() }

        emit(DataResult.Loading(data = wordInfos))

        try {
            val remoteWordInfos = dictionaryApi.getWordInfo(word)
            wordInfoDao.deleteWordInfos(remoteWordInfos.map { it.word })
            wordInfoDao.insertWordInfos(remoteWordInfos.map { it.toWordInfoEntity() })
        } catch (e: Exception) {
            emit(DataResult.Error(e))
        }

        val newWordInfos = wordInfoDao.getWordInfos(word).map { it.toWordInfo() }
        emit(DataResult.Success(newWordInfos))
    }
}