package com.jj.templateproject.notes.featurenote.domain.usecases

import com.jj.templateproject.notes.featurenote.domain.repository.NoteRepository

class GetSingleNoteUseCase(private val noteRepository: NoteRepository) {

    suspend operator fun invoke(id: Int) = noteRepository.getNoteById(id)
}