package com.jj.templateproject.networking.framework.viewmodels

import androidx.lifecycle.viewModelScope
import com.jj.templateproject.core.framework.presentation.viewmodels.BaseViewAction
import com.jj.templateproject.core.framework.presentation.viewmodels.BaseViewModel
import com.jj.templateproject.core.framework.presentation.viewmodels.ViewEvent
import com.jj.templateproject.core.framework.presentation.viewmodels.event
import com.jj.templateproject.core.framework.presentation.viewmodels.states.BaseViewState
import com.jj.templateproject.networking.domain.usecases.FishResultsUseCases
import com.jj.templateproject.networking.framework.presentation.adapters.fishlistitems.FishItemViewData
import com.jj.templateproject.networking.framework.viewmodels.ApiResultsViewModel.ViewAction
import com.jj.templateproject.networking.framework.viewmodels.ApiResultsViewModel.ViewAction.FetchingChanged
import com.jj.templateproject.networking.framework.viewmodels.ApiResultsViewModel.ViewAction.FetchingError
import com.jj.templateproject.networking.framework.viewmodels.ApiResultsViewModel.ViewState
import kotlinx.coroutines.launch

class ApiResultsViewModel(private val fishResultsUseCases: FishResultsUseCases) : BaseViewModel<ViewState, ViewAction>(ViewState()) {

    data class ViewState(
            val loadingSpecies: Boolean = false,
            val fishItemsList: List<FishItemViewData> = listOf(),
            val loadingError: ViewEvent<String>? = null
    ) : BaseViewState

    sealed class ViewAction : BaseViewAction {
        class FetchingChanged(val isLoading: Boolean, val fishResults: List<FishItemViewData> = listOf()) : ViewAction()
        class FetchingError(val errorMessage: String) : ViewAction()
    }

    override fun reduceState(viewAction: ViewAction): ViewState =
        when (viewAction) {
            is FetchingChanged -> state.copy(loadingSpecies = viewAction.isLoading, fishItemsList = viewAction.fishResults)
            is FetchingError -> state.copy(loadingError = viewAction.errorMessage.event())
        }

    fun fetchSpecies() {
        viewModelScope.launch {
            sendViewAction(FetchingChanged(true))
            fishResultsUseCases.getTwoFishResultsUseCase().onSuccess {
                sendViewAction(FetchingChanged(false, dataValue))
            }.onError {
                sendViewAction(FetchingChanged(false, dataValue))
                sendViewAction(FetchingError(exception.localizedMessage?.let { "Error: $it" } ?: "Fetching error"))
            }
        }
    }
}