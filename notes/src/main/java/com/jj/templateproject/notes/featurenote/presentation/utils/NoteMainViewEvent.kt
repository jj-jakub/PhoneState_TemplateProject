package com.jj.templateproject.notes.featurenote.presentation.utils

import com.jj.templateproject.notes.featurenote.domain.model.Note
import com.jj.templateproject.notes.featurenote.domain.utils.NoteOrder

sealed class NoteMainViewEvent {
    data class OrderChanged(val noteOrder: NoteOrder) : NoteMainViewEvent()
    data class DeleteNote(val note: Note): NoteMainViewEvent()
    object RestoreNote: NoteMainViewEvent()
    object ToggleOrderSection: NoteMainViewEvent()
}
