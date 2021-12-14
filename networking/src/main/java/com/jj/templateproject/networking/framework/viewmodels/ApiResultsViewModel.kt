package com.jj.templateproject.networking.framework.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jj.templateproject.core.framework.presentation.viewmodels.BaseViewModel
import com.jj.templateproject.networking.data.repositories.FishDataRepository
import kotlinx.coroutines.launch

class ApiResultsViewModel(private val fishDataRepository: FishDataRepository) : BaseViewModel() {

    private val stateMutableLiveData = MutableLiveData<ViewState>()
    val stateLiveData: LiveData<ViewState> = stateMutableLiveData

    data class ViewState(
            val loadingSpecies: Boolean
    )

    fun fetchSpecies() {
        viewModelScope.launch {
            stateMutableLiveData.value = ViewState(true)
            // TODO Handle error by repo
            try {
                fishDataRepository.fetchSpecifiedSpeciesInfo("red-snapper")
            } catch (e: Exception) {
                Log.e("ABAB", "e", e)
            }
            stateMutableLiveData.value = ViewState(false)
        }
    }
}