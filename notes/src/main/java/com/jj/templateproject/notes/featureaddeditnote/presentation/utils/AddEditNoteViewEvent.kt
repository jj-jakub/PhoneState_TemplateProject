package com.jj.templateproject.notes.featureaddeditnote.presentation.utils

sealed class AddEditNoteViewEvent {
    data class EnteredTitleCharacter(val currentTitle: String): AddEditNoteViewEvent()
    data class ChangeTitleFocus(val hasFocus: Boolean): AddEditNoteViewEvent()
    data class EnteredContentCharacter(val currentContent: String): AddEditNoteViewEvent()
    data class ChangeContentFocus(val hasFocus: Boolean): AddEditNoteViewEvent()
    data class ChangeColor(val color: Int): AddEditNoteViewEvent()
    data class LoadNote(val noteToEditId: Int) : AddEditNoteViewEvent()
    object SaveNote: AddEditNoteViewEvent()
}