package com.jj.templateproject.notes.featurenote.presentation.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.jj.templateproject.notes.databinding.LayoutNoteItemBinding
import com.jj.templateproject.notes.featurenote.presentation.utils.NoteViewData

class NoteViewHolder(private val binding: LayoutNoteItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(noteViewData: NoteViewData, onItemClick: () -> Unit, onDeleteButtonClick: () -> Unit) {
        with(binding) {
            noteTitle.text = noteViewData.title
            noteContent.text = noteViewData.content
            noteInnerContainer.setBackgroundResource(noteViewData.color)
            noteInnerContainer.setOnClickListener { onItemClick() }
            deleteNoteButton.setOnClickListener { onDeleteButtonClick() }
        }
    }
}