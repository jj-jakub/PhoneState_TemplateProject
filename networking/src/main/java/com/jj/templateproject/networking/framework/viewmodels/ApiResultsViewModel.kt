package com.jj.templateproject.networking.framework.viewmodels

import androidx.lifecycle.viewModelScope
import com.jj.templateproject.core.data.text.TextHelper
import com.jj.templateproject.core.framework.presentation.viewmodels.BaseViewAction
import com.jj.templateproject.core.framework.presentation.viewmodels.BaseViewModel
import com.jj.templateproject.core.framework.presentation.viewmodels.states.BaseViewState
import com.jj.templateproject.networking.data.repositories.FishDataRepository
import com.jj.templateproject.networking.domain.fishdata.FishDataResponseItem
import com.jj.templateproject.networking.framework.presentation.adapters.fishlistitems.DefaultFishItemViewData
import com.jj.templateproject.networking.framework.presentation.adapters.fishlistitems.FishItemViewData
import com.jj.templateproject.networking.framework.viewmodels.ApiResultsViewModel.ViewAction
import com.jj.templateproject.networking.framework.viewmodels.ApiResultsViewModel.ViewAction.FetchingChanged
import com.jj.templateproject.networking.framework.viewmodels.ApiResultsViewModel.ViewState
import kotlinx.coroutines.launch

class ApiResultsViewModel(private val fishDataRepository: FishDataRepository,
        private val textHelper: TextHelper) : BaseViewModel<ViewState, ViewAction>(ViewState()) {

    data class ViewState(
            val loadingSpecies: Boolean = false,
            val fishItemsList: List<FishItemViewData> = listOf()
    ) : BaseViewState

    sealed class ViewAction : BaseViewAction {
        class FetchingChanged(val isLoading: Boolean, val fishResults: List<FishItemViewData> = listOf()) : ViewAction()
    }

    override fun reduceState(viewAction: ViewAction): ViewState =
        when (viewAction) {
            is FetchingChanged -> state.copy(loadingSpecies = viewAction.isLoading, fishItemsList = viewAction.fishResults)
        }

    fun fetchSpecies() {
        viewModelScope.launch {
            sendViewAction(FetchingChanged(true))
            sendViewAction(FetchingChanged(false, getFishResults()))
        }
    }

    private suspend fun getFishResults(): List<FishItemViewData> {
        val fishResults: ArrayList<FishDataResponseItem> = arrayListOf()
        with(fishDataRepository) {
            fetchSpecifiedSpeciesInfo("red-snapper").onSuccess {
                getValue()?.let { list -> fishResults.addAll(list) }
            }
            fetchSpecifiedSpeciesInfo("canary-rockfish").onSuccess {
                getValue()?.let { list -> fishResults.addAll(list) }
            }
        }
        return fishResults.map {
            DefaultFishItemViewData(textHelper.htmlToString(it.speciesName), textHelper.htmlToString(it.biology),
                    textHelper.htmlToString(it.nOAAFisheriesRegion), textHelper.htmlToString(it.sugarsTotal),
                    textHelper.htmlToString(it.taste))
        }
    }
}