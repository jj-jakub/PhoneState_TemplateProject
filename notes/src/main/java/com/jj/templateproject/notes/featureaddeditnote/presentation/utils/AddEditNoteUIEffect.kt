package com.jj.templateproject.notes.featureaddeditnote.presentation.utils

sealed class AddEditNoteUIEffect {
    data class ShowToast(val message: String) :AddEditNoteUIEffect()
    object SaveNote: AddEditNoteUIEffect()
}