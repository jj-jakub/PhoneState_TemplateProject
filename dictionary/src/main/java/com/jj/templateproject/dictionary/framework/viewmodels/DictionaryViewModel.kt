package com.jj.templateproject.dictionary.framework.viewmodels

import androidx.lifecycle.viewModelScope
import com.jj.templateproject.core.domain.result.UseCaseResult.Error
import com.jj.templateproject.core.domain.result.UseCaseResult.Loading
import com.jj.templateproject.core.domain.result.UseCaseResult.Success
import com.jj.templateproject.core.framework.presentation.viewmodels.BaseViewAction
import com.jj.templateproject.core.framework.presentation.viewmodels.BaseViewModel
import com.jj.templateproject.core.framework.presentation.viewmodels.states.BaseViewState
import com.jj.templateproject.dictionary.domain.model.WordInfo
import com.jj.templateproject.dictionary.domain.usecases.GetWordInfoUseCase
import com.jj.templateproject.dictionary.framework.viewmodels.DictionaryViewModel.UIEvent.ShowSnackbar
import com.jj.templateproject.dictionary.framework.viewmodels.DictionaryViewModel.ViewAction
import com.jj.templateproject.dictionary.framework.viewmodels.DictionaryViewModel.ViewAction.DataLoaded
import com.jj.templateproject.dictionary.framework.viewmodels.DictionaryViewModel.ViewAction.DataLoading
import com.jj.templateproject.dictionary.framework.viewmodels.DictionaryViewModel.ViewState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

private const val SEARCH_DELAY_MILLIS = 500L

class DictionaryViewModel(private val getWordInfoUseCase: GetWordInfoUseCase) : BaseViewModel<ViewState, ViewAction>(ViewState()) {

    data class ViewState(
            val isLoading: Boolean = false,
            val wordInfoItems: List<WordInfo> = emptyList()) : BaseViewState

    sealed class ViewAction : BaseViewAction {
        class DataLoading(val data: List<WordInfo>) : ViewAction()
        class DataLoaded(val data: List<WordInfo>) : ViewAction()
    }

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class UIEvent {
        data class ShowSnackbar(val message: String) : UIEvent()
    }

    override fun reduceState(viewAction: ViewAction): ViewState =
        when (viewAction) {
            is DataLoaded -> state.copy(isLoading = false, wordInfoItems = viewAction.data)
            is DataLoading -> state.copy(isLoading = true, wordInfoItems = viewAction.data)
        }

    private var searchJob: Job? = null

    fun onSearch(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DELAY_MILLIS)
            getWordInfoUseCase(query).onEach { result ->
                when (result) {
                    is Loading -> sendViewAction(DataLoading(result.dataValue ?: emptyList()))
                    is Success -> sendViewAction(DataLoaded(result.dataValue))
                    is Error -> {
                        sendViewAction(DataLoaded(result.dataValue))
                        sendUiEvent(ShowSnackbar(result.exception.localizedMessage ?: "Network error occurred"))
                    }
                }
            }.launchIn(this)
        }
    }

    private fun sendUiEvent(uiEvent: UIEvent) {
        viewModelScope.launch {
            _eventFlow.emit(uiEvent)
        }
    }
}