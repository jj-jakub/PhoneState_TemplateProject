package com.jj.templateproject.core.framework.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jj.templateproject.core.framework.presentation.viewmodels.states.BaseViewState

abstract class BaseViewModel<ViewState : BaseViewState, ViewAction : BaseViewAction>(private val initialState: ViewState) : ViewModel() {

    protected val stateMutableLiveData = MutableLiveData(initialState)
    val stateLiveData: LiveData<ViewState> = stateMutableLiveData

    protected var state: ViewState = initialState
        set(value) {
            if (state != value) {
                field = value
                stateMutableLiveData.value = value
            }
        }

    open fun reduceState(viewAction: ViewAction): ViewState = state

    fun sendViewAction(viewAction: ViewAction) {
        state = reduceState(viewAction)
    }
}