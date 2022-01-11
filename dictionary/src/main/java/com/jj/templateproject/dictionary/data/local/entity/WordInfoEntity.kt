package com.jj.templateproject.dictionary.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jj.templateproject.dictionary.domain.model.Meaning
import com.jj.templateproject.dictionary.domain.model.WordInfo

@Entity
data class WordInfoEntity(
        val word: String,
        val phonetic: String,
        val origin: String,
        val meanings: List<Meaning>,
        @PrimaryKey val id: Int? = null
) {
    fun toWordInfo() = WordInfo(meaning = meanings, origin = origin, phonetic = phonetic, word = word)
}