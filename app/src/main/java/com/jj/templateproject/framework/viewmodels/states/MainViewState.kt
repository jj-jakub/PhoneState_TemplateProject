package com.jj.templateproject.framework.viewmodels.states

data class MainViewState(
    val networkViewState: NetworkViewState = NetworkViewState(),
    val airplaneViewState: AirplaneViewState = AirplaneViewState()
) : BaseViewState