package com.jj.templateproject.notes.featurenote.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.jj.templateproject.core.framework.presentation.viewmodels.BaseViewAction
import com.jj.templateproject.core.framework.presentation.viewmodels.BaseViewModel
import com.jj.templateproject.core.framework.presentation.viewmodels.states.BaseViewState
import com.jj.templateproject.notes.featurenote.domain.model.Note
import com.jj.templateproject.notes.featurenote.domain.usecases.NoteUseCases
import com.jj.templateproject.notes.featurenote.domain.utils.NoteOrder
import com.jj.templateproject.notes.featurenote.domain.utils.OrderType
import com.jj.templateproject.notes.featurenote.presentation.utils.NoteMainViewEvent
import com.jj.templateproject.notes.featurenote.presentation.utils.NoteMainViewEvent.DeleteNote
import com.jj.templateproject.notes.featurenote.presentation.utils.NoteMainViewEvent.RestoreNote
import com.jj.templateproject.notes.featurenote.presentation.utils.NoteMainViewEvent.ToggleOrderSection
import com.jj.templateproject.notes.featurenote.presentation.viewmodels.NotesMainViewModel.ViewAction
import com.jj.templateproject.notes.featurenote.presentation.viewmodels.NotesMainViewModel.ViewAction.NoteDeleted
import com.jj.templateproject.notes.featurenote.presentation.viewmodels.NotesMainViewModel.ViewAction.NoteRestored
import com.jj.templateproject.notes.featurenote.presentation.viewmodels.NotesMainViewModel.ViewAction.NotesLoaded
import com.jj.templateproject.notes.featurenote.presentation.viewmodels.NotesMainViewModel.ViewAction.NotesLoading
import com.jj.templateproject.notes.featurenote.presentation.viewmodels.NotesMainViewModel.ViewAction.OrderChanged
import com.jj.templateproject.notes.featurenote.presentation.viewmodels.NotesMainViewModel.ViewAction.OrderSectionVisibilityChanged
import com.jj.templateproject.notes.featurenote.presentation.viewmodels.NotesMainViewModel.ViewState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class NotesMainViewModel(private val noteUseCases: NoteUseCases) : BaseViewModel<ViewState, ViewAction>(ViewState()) {

    data class ViewState(
            val isLoading: Boolean = false,
            val notes: List<Note> = emptyList(),
            val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
            val isOrderSectionVisible: Boolean = false,
            val undoDeleteNotePossible: Boolean = false
    ) : BaseViewState

    sealed class ViewAction : BaseViewAction {
        class OrderChanged(val noteOrder: NoteOrder) : ViewAction()
        class OrderSectionVisibilityChanged(val isVisible: Boolean) : ViewAction()
        class NotesLoaded(val notes: List<Note>) : ViewAction()
        class NotesLoading(val notes: List<Note> = emptyList()) : ViewAction()
        object NoteDeleted : ViewAction()
        object NoteRestored: ViewAction()
    }

    private var recentlyDeletedNote: Note? = null

    private var noteObservingJob: Job? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    override fun reduceState(viewAction: ViewAction): ViewState =
        when (viewAction) {
            is OrderChanged -> state.copy(noteOrder = viewAction.noteOrder)
            is NotesLoaded -> state.copy(isLoading = false, notes = viewAction.notes)
            is OrderSectionVisibilityChanged -> state.copy(isOrderSectionVisible = viewAction.isVisible)
            is NotesLoading -> state.copy(isLoading = true, notes = viewAction.notes)
            NoteDeleted -> state.copy(undoDeleteNotePossible = true)
            NoteRestored -> state.copy(undoDeleteNotePossible = false)
        }

    fun onEvent(event: NoteMainViewEvent) {
        when (event) {
            is NoteMainViewEvent.OrderChanged -> changeOrder(event.noteOrder)
            is DeleteNote -> deleteNote(event.note)
            RestoreNote -> restoreNote()
            ToggleOrderSection -> toggleOrderSection()
        }
    }

    private fun changeOrder(noteOrder: NoteOrder) {
        sendViewAction(OrderChanged(noteOrder = noteOrder))
        getNotes(noteOrder)
    }

    private fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteUseCases.deleteNoteUseCase(note)
            recentlyDeletedNote = note
            sendViewAction(NoteDeleted)
        }
    }

    private fun restoreNote() {
        recentlyDeletedNote?.let { noteToRestore ->
            viewModelScope.launch {
                noteUseCases.addNoteUseCase(noteToRestore)
                recentlyDeletedNote = null
                sendViewAction(NoteRestored)
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        sendViewAction(NotesLoading())
        noteObservingJob?.cancel()
        noteObservingJob = noteUseCases.getNotesUseCase(noteOrder).onEach { notes ->
            sendViewAction(NotesLoaded(notes = notes))
        }.launchIn(viewModelScope)
    }

    private fun toggleOrderSection() {
        sendViewAction(OrderSectionVisibilityChanged(!state.isOrderSectionVisible))
    }
}
