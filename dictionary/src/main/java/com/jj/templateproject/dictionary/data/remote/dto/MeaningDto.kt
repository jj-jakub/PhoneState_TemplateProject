package com.jj.templateproject.dictionary.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.jj.templateproject.dictionary.domain.model.Meaning

data class MeaningDto(
        @SerializedName("definitions")
        val definitions: List<DefinitionDto>,
        @SerializedName("partOfSpeech")
        val partOfSpeech: String
) {
    fun toMeaning() = Meaning(definitions.map { it.toDefinition() }, partOfSpeech)
}