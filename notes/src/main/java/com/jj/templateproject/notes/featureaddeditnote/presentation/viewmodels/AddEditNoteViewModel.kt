package com.jj.templateproject.notes.featureaddeditnote.presentation.viewmodels

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
import com.jj.templateproject.notes.featureaddeditnote.presentation.viewmodels.AddEditNoteViewModel.ViewAction.NoteContentChanged
import com.jj.templateproject.notes.featureaddeditnote.presentation.viewmodels.AddEditNoteViewModel.ViewAction.NoteContentFieldFocusChanged
import com.jj.templateproject.notes.featureaddeditnote.presentation.viewmodels.AddEditNoteViewModel.ViewAction.NoteTitleChanged
import com.jj.templateproject.notes.featureaddeditnote.presentation.viewmodels.AddEditNoteViewModel.ViewAction.NoteTitleFieldFocusChanged
import com.jj.templateproject.notes.featureaddeditnote.presentation.viewmodels.AddEditNoteViewModel.ViewState
import com.jj.templateproject.notes.featurenote.domain.model.Note
import com.jj.templateproject.notes.featurenote.domain.usecases.NoteUseCases
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class AddEditNoteViewModel(private val useCases: NoteUseCases) : BaseViewModel<ViewState, ViewAction>(ViewState()) {

    data class ViewState(
            val isLoading: Boolean = false,
            val currentlySelectedColor: Int = Note.noteColors.random(),
            val noteTitle: String = "",
            val noteContent: String = "",
            val noteTitleState: AddEditNoteTextFieldState = AddEditNoteTextFieldState(),
            val noteContentState: AddEditNoteTextFieldState = AddEditNoteTextFieldState(),
    ) : BaseViewState

    sealed class ViewAction : BaseViewAction {
        class NoteTitleChanged(val currentTitle: String) : ViewAction()
        class NoteContentChanged(val currentContent: String) : ViewAction()
        class NoteTitleFieldFocusChanged(val hasFocus: Boolean) : ViewAction()
        class NoteContentFieldFocusChanged(val hasFocus: Boolean) : ViewAction()
    }

    override fun reduceState(viewAction: ViewAction): ViewState =
        when (viewAction) {
            is NoteTitleChanged -> state.copy(noteTitleState = state.noteTitleState.copy(content = viewAction.currentTitle))
            is NoteContentChanged -> state.copy(noteTitleState = state.noteContentState.copy(content = viewAction.currentContent))
            is NoteTitleFieldFocusChanged -> state.copy(noteTitleState = state.noteTitleState.copy(
                    isHintVisible = viewAction.hasFocus && state.noteTitleState.content.isEmpty()))
            is NoteContentFieldFocusChanged ->  state.copy(noteTitleState = state.noteContentState.copy(
                    isHintVisible = viewAction.hasFocus && state.noteContentState.content.isEmpty()))
        }

    private val mutableAddEditNoteUIEffects = MutableSharedFlow<AddEditNoteUIEffect>()
    val addEditNoteUIEffects = mutableAddEditNoteUIEffects.asSharedFlow()

    fun onEvent(event: AddEditNoteViewEvent) {
        when (event) {
            is EnteredTitleCharacter -> sendViewAction(NoteTitleChanged(event.currentTitle))
            is EnteredContentCharacter -> sendViewAction(NoteTitleChanged(event.currentContent))
            is ChangeTitleFocus -> sendViewAction(NoteTitleFieldFocusChanged(event.hasFocus))
            is ChangeContentFocus -> sendViewAction(NoteContentFieldFocusChanged(event.hasFocus))
            is ChangeColor -> TODO()
            SaveNote -> TODO()
        }
    }

}