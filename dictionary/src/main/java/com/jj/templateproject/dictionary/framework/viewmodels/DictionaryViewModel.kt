package com.jj.templateproject.dictionary.framework.viewmodels

import com.jj.templateproject.core.framework.presentation.viewmodels.BaseViewAction
import com.jj.templateproject.core.framework.presentation.viewmodels.BaseViewModel
import com.jj.templateproject.core.framework.presentation.viewmodels.states.BaseViewState
import com.jj.templateproject.dictionary.framework.viewmodels.DictionaryViewModel.ViewAction
import com.jj.templateproject.dictionary.framework.viewmodels.DictionaryViewModel.ViewState

class DictionaryViewModel : BaseViewModel<ViewState, ViewAction>(ViewState()) {

    data class ViewState(val isLoading: Boolean = false) : BaseViewState

    sealed class ViewAction : BaseViewAction

}