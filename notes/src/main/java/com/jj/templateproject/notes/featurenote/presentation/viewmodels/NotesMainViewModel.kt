package com.jj.templateproject.notes.featurenote.presentation.viewmodels

import com.jj.templateproject.core.framework.presentation.viewmodels.BaseViewAction
import com.jj.templateproject.core.framework.presentation.viewmodels.BaseViewModel
import com.jj.templateproject.core.framework.presentation.viewmodels.states.BaseViewState
import com.jj.templateproject.notes.featurenote.presentation.viewmodels.NotesMainViewModel.ViewAction
import com.jj.templateproject.notes.featurenote.presentation.viewmodels.NotesMainViewModel.ViewState

class NotesMainViewModel : BaseViewModel<ViewState, ViewAction>(ViewState()) {

    data class ViewState(
            val isLoading: Boolean = false
    ) : BaseViewState

    sealed class ViewAction : BaseViewAction
}