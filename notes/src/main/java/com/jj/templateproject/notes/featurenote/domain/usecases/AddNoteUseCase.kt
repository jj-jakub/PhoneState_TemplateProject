package com.jj.templateproject.notes.featurenote.domain.usecases

import com.jj.templateproject.notes.featurenote.domain.exceptions.InvalidNoteException
import com.jj.templateproject.notes.featurenote.domain.model.Note
import com.jj.templateproject.notes.featurenote.domain.repository.NoteRepository

class AddNoteUseCase(private val noteRepository: NoteRepository) {

    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) throw InvalidNoteException("Title cannot be empty.")
        if (note.content.isBlank()) throw InvalidNoteException("Content cannot be empty.")
        noteRepository.insertNote(note)
    }
}