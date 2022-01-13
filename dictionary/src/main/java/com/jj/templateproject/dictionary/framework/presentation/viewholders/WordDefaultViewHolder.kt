package com.jj.templateproject.dictionary.framework.presentation.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.jj.templateproject.dictionary.databinding.WordDefaultViewHolderBinding
import com.jj.templateproject.dictionary.domain.model.WordInfo

class WordDefaultViewHolder(private val binding: WordDefaultViewHolderBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: WordInfo) {
        with(binding) {
            wordValue.text = item.word
            wordPhoneticValue.text = item.phonetic
            wordOriginValue.text = item.origin
            wordMeaningValue.text = item.meaning.joinToString(separator = "\n") {
                it.definition.joinToString(separator = "\n") { def ->
                    "Definition: " +
                            "${def.definition}\nExample: ${def.example}\nSynonyms: ${def.synonyms}\nAntonyms: ${def.antonyms}\n"
                } + "Part of speech: ${it.partOfSpeech}"
            }
        }
    }
}