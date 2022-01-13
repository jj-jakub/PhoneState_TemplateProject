package com.jj.templateproject.dictionary.framework.presentation.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jj.templateproject.core.framework.presentation.createBinding
import com.jj.templateproject.dictionary.databinding.WordDefaultViewHolderBinding
import com.jj.templateproject.dictionary.domain.model.WordInfo
import com.jj.templateproject.dictionary.framework.presentation.viewholders.WordDefaultViewHolder

class WordListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items: ArrayList<WordInfo> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return WordDefaultViewHolder(parent.createBinding(WordDefaultViewHolderBinding::inflate))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        (holder as WordDefaultViewHolder).bind(item)
    }

    override fun getItemCount() = items.size

    fun setItems(newItems: List<WordInfo>) {
        items.clear()
        items.addAll(newItems)
    }
}