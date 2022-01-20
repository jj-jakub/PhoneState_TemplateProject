package com.jj.templateproject.notes.featurenote.presentation.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jj.templateproject.core.framework.presentation.createBinding
import com.jj.templateproject.notes.databinding.LayoutNoteItemBinding
import com.jj.templateproject.notes.featurenote.domain.model.Note
import com.jj.templateproject.notes.featurenote.presentation.utils.NoteViewData
import com.jj.templateproject.notes.featurenote.presentation.viewholders.NoteViewHolder

class NoteAdapter(
        private val onItemClickListener: (Note) -> Unit,
        private val onDeleteClickListener: (Note) -> Unit) : RecyclerView.Adapter<NoteViewHolder>() {

    private val items: ArrayList<Note> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(parent.createBinding(LayoutNoteItemBinding::inflate))
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = items[position]
        holder.bind(
                noteViewData = NoteViewData(note.title, note.content, note.color),
                onItemClick = { onItemClickListener.invoke(note) },
                onDeleteButtonClick = { onDeleteClickListener.invoke(note) })
    }

    override fun getItemCount(): Int = items.size

    fun setItems(newItems: List<Note>) {
        items.clear()
        items.addAll(newItems)
    }
}