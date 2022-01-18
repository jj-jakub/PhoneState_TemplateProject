package com.jj.templateproject.notes.featurenote.domain.usecases

import com.jj.templateproject.notes.featurenote.domain.model.Note
import com.jj.templateproject.notes.featurenote.domain.repository.NoteRepository

class DeleteNoteUseCase(
        private val noteRepository: NoteRepository
) {

    suspend operator fun invoke(note: Note) {
        noteRepository.deleteNote(note)
    }
}