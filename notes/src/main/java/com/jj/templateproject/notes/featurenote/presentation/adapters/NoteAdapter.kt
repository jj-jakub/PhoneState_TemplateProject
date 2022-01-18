package com.jj.templateproject.notes.featurenote.presentation.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jj.templateproject.core.framework.presentation.createBinding
import com.jj.templateproject.notes.databinding.LayoutNoteItemBinding
import com.jj.templateproject.notes.featurenote.presentation.utils.NoteViewData
import com.jj.templateproject.notes.featurenote.presentation.viewholders.NoteViewHolder

class NoteAdapter : RecyclerView.Adapter<NoteViewHolder>() {

    private val items: ArrayList<NoteViewData> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(parent.createBinding(LayoutNoteItemBinding::inflate))
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun setItems(newItems: List<NoteViewData>) {
        items.clear()
        items.addAll(newItems)
    }
}