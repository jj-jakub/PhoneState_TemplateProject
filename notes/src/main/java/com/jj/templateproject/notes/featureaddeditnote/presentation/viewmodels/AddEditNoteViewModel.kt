package com.jj.templateproject.notes.featureaddeditnote.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.jj.templateproject.core.framework.presentation.viewmodels.BaseViewAction
import com.jj.templateproject.core.framework.presentation.viewmodels.BaseViewModel
import com.jj.templateproject.core.framework.presentation.viewmodels.states.BaseViewState
import com.jj.templateproject.notes.featureaddeditnote.presentation.utils.AddEditNoteTextFieldState
import com.jj.templateproject.notes.featureaddeditnote.presentation.utils.AddEditNoteUIEffect
import com.jj.templateproject.notes.featureaddeditnote.presentation.utils.AddEditNoteViewEvent
import com.jj.templateproject.notes.featureaddeditnote.presentation.utils.AddEditNoteViewEvent.ChangeColor
import com.jj.templateproject.notes.featureaddeditnote.presentation.utils.AddEditNoteViewEvent.ChangeContentFocus
import com.jj.templateproject.notes.featureaddeditnote.presentation.utils.AddEditNoteViewEvent.ChangeTitleFocus
import com.jj.templateproject.notes.featureaddeditnote.presentation.utils.AddEditNoteViewEvent.EnteredContentCharacter
import com.jj.templateproject.notes.featureaddeditnote.presentation.utils.AddEditNoteViewEvent.EnteredTitleCharacter
import com.jj.templateproject.notes.featureaddeditnote.presentation.utils.AddEditNoteViewEvent.SaveNote
import com.jj.templateproject.notes.featureaddeditnote.presentation.viewmodels.AddEditNoteViewModel.ViewAction
import com.jj.templateproject.notes.featureaddeditnote.presentation.viewmodels.AddEditNoteViewModel.ViewAction.NoteColorChanged
import com.jj.templateproject.notes.featureaddeditnote.presentation.viewmodels.AddEditNoteViewModel.ViewAction.NoteContentChanged
import com.jj.templateproject.notes.featureaddeditnote.presentation.viewmodels.AddEditNoteViewModel.ViewAction.NoteContentFieldFocusChanged
import com.jj.templateproject.notes.featureaddeditnote.presentation.viewmodels.AddEditNoteViewModel.ViewAction.NoteTitleChanged
import com.jj.templateproject.notes.featureaddeditnote.presentation.viewmodels.AddEditNoteViewModel.ViewAction.NoteTitleFieldFocusChanged
import com.jj.templateproject.notes.featureaddeditnote.presentation.viewmodels.AddEditNoteViewModel.ViewState
import com.jj.templateproject.notes.featurenote.domain.exceptions.InvalidNoteException
import com.jj.templateproject.notes.featurenote.domain.model.Note
import com.jj.templateproject.notes.featurenote.domain.usecases.NoteUseCases
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.Date

class AddEditNoteViewModel(private val useCases: NoteUseCases) : BaseViewModel<ViewState, ViewAction>(ViewState()) {

    data class ViewState(
            val isLoading: Boolean = false,
            val currentlySelectedColor: Int = Note.noteColors.random(),
            val noteTitleState: AddEditNoteTextFieldState = AddEditNoteTextFieldState(hint = "Enter title"),
            val noteContentState: AddEditNoteTextFieldState = AddEditNoteTextFieldState(hint = "Enter content"),
    ) : BaseViewState

    sealed class ViewAction : BaseViewAction {
        class NoteTitleChanged(val currentTitle: String) : ViewAction()
        class NoteContentChanged(val currentContent: String) : ViewAction()
        class NoteTitleFieldFocusChanged(val hasFocus: Boolean) : ViewAction()
        class NoteContentFieldFocusChanged(val hasFocus: Boolean) : ViewAction()
        class NoteColorChanged(val color: Int) : ViewAction()
    }

    override fun reduceState(viewAction: ViewAction): ViewState =
        when (viewAction) {
            is NoteTitleChanged -> state.copy(noteTitleState = state.noteTitleState.copy(content = viewAction.currentTitle))
            is NoteContentChanged -> state.copy(noteTitleState = state.noteContentState.copy(content = viewAction.currentContent))
            is NoteTitleFieldFocusChanged -> state.copy(noteTitleState = state.noteTitleState.copy(
                    isHintVisible = viewAction.hasFocus && state.noteTitleState.content.isEmpty()))
            is NoteContentFieldFocusChanged -> state.copy(noteTitleState = state.noteContentState.copy(
                    isHintVisible = viewAction.hasFocus && state.noteContentState.content.isEmpty()))
            is NoteColorChanged -> state.copy(currentlySelectedColor = viewAction.color)
        }

    private val mutableAddEditNoteUIEffects = MutableSharedFlow<AddEditNoteUIEffect>()
    val addEditNoteUIEffects = mutableAddEditNoteUIEffects.asSharedFlow()

    private var currentNoteId: Int? = null

    fun onEvent(event: AddEditNoteViewEvent) {
        when (event) {
            is EnteredTitleCharacter -> sendViewAction(NoteTitleChanged(event.currentTitle))
            is EnteredContentCharacter -> sendViewAction(NoteTitleChanged(event.currentContent))
            is ChangeTitleFocus -> sendViewAction(NoteTitleFieldFocusChanged(event.hasFocus))
            is ChangeContentFocus -> sendViewAction(NoteContentFieldFocusChanged(event.hasFocus))
            is ChangeColor -> sendViewAction(NoteColorChanged(event.color))
            SaveNote -> saveNote()
        }
    }

    private fun saveNote() {
        viewModelScope.launch {
            try {
                useCases.addNoteUseCase(
                        Note(
                                title = state.noteTitleState.content,
                                content = state.noteContentState.content,
                                timestamp = Date().time,
                                color = state.currentlySelectedColor,
                                id = currentNoteId
                        )
                )
                mutableAddEditNoteUIEffects.emit(AddEditNoteUIEffect.SaveNote)
            } catch (e: InvalidNoteException) {
                mutableAddEditNoteUIEffects.emit(AddEditNoteUIEffect.ShowToast(message = e.localizedMessage ?: "Can't save note"))
            }
        }
    }
}