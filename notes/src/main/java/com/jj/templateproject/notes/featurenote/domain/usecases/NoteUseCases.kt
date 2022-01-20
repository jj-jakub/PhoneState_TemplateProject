package com.jj.templateproject.notes.featurenote.domain.usecases

data class NoteUseCases(
        val getNotesUseCase: GetNotesUseCase,
        val addNoteUseCase: AddNoteUseCase,
        val deleteNoteUseCase: DeleteNoteUseCase,
        val getSingleNoteUseCase: GetSingleNoteUseCase
)