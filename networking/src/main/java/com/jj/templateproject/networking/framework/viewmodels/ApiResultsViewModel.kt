package com.jj.templateproject.networking.framework.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jj.templateproject.core.data.text.TextHelper
import com.jj.templateproject.core.framework.presentation.viewmodels.BaseViewModel
import com.jj.templateproject.networking.data.repositories.FishDataRepository
import com.jj.templateproject.networking.domain.fishdata.FishDataResponseItem
import com.jj.templateproject.networking.framework.presentation.adapters.fishlistitems.DefaultFishItemViewData
import com.jj.templateproject.networking.framework.presentation.adapters.fishlistitems.FishItemViewData
import kotlinx.coroutines.launch

class ApiResultsViewModel(private val fishDataRepository: FishDataRepository,
        private val textHelper: TextHelper) : BaseViewModel() {

    private val stateMutableLiveData = MutableLiveData<ViewState>()
    val stateLiveData: LiveData<ViewState> = stateMutableLiveData

    data class ViewState(
            val loadingSpecies: Boolean,
            val fishItemsList: List<FishItemViewData>
    )

    fun fetchSpecies() {
        viewModelScope.launch {
            stateMutableLiveData.value = ViewState(true, listOf())
            val fishResults: List<FishItemViewData> = getFishResults()
            stateMutableLiveData.value = ViewState(false, fishResults)
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