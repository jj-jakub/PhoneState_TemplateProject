package com.jj.templateproject.dictionary.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.jj.templateproject.dictionary.data.local.entity.WordInfoEntity

data class WordInfoDto(
        @SerializedName("meanings")
        val meaning: List<MeaningDto>,
        @SerializedName("origin")
        val origin: String,
        @SerializedName("phonetic")
        val phonetic: String,
        @SerializedName("phonetics")
        val phonetics: List<PhoneticDto>,
        @SerializedName("word")
        val word: String
) {
    fun toWordInfoEntity() = WordInfoEntity(meanings = meaning.map { it.toMeaning() }, origin = origin, phonetic = phonetic, word = word)
}