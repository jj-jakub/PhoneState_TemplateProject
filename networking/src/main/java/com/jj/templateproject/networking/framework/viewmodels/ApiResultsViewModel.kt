package com.jj.templateproject.networking.framework.viewmodels

import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.text.Html
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jj.templateproject.core.framework.presentation.viewmodels.BaseViewModel
import com.jj.templateproject.networking.data.repositories.FishDataRepository
import com.jj.templateproject.networking.framework.presentation.adapters.fishlistitems.DefaultFishItemViewData
import com.jj.templateproject.networking.framework.presentation.adapters.fishlistitems.FishItemViewData
import kotlinx.coroutines.launch

class ApiResultsViewModel(private val fishDataRepository: FishDataRepository) : BaseViewModel() {

    private val stateMutableLiveData = MutableLiveData<ViewState>()
    val stateLiveData: LiveData<ViewState> = stateMutableLiveData

    data class ViewState(
            val loadingSpecies: Boolean,
            val fishItemsList: List<FishItemViewData>
    )

    fun fetchSpecies() {
        viewModelScope.launch {
            var fishResults: List<FishItemViewData> = listOf()
            stateMutableLiveData.value = ViewState(true, listOf())
            // TODO Handle error by repo
            try {
                val fishDataResponseItemList = fishDataRepository.fetchSpecifiedSpeciesInfo("red-snapper").result
                fishResults = fishDataResponseItemList.map {
                    DefaultFishItemViewData(htmlToString(it.speciesName), htmlToString(it.biology), htmlToString(it.nOAAFisheriesRegion),
                            htmlToString(it.sugarsTotal), htmlToString(it.taste))
                }
            } catch (e: Exception) {
                Log.e("ABAB", "e", e)
            }
            stateMutableLiveData.value = ViewState(false, fishResults)
        }
    }

    private fun htmlToString(htmlString: String?): String? {
        val pureString = when {
            htmlString == null -> null
            VERSION.SDK_INT >= VERSION_CODES.N -> Html.fromHtml(htmlString, Html.FROM_HTML_MODE_LEGACY).toString()
            else -> Html.fromHtml(htmlString).toString()
        }
        return pureString?.trim()
    }
}