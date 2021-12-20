package com.jj.templateproject.monitoring.framework.viewmodels

import com.jj.templateproject.core.framework.presentation.viewmodels.BaseViewAction
import com.jj.templateproject.core.framework.presentation.viewmodels.BaseViewModel
import com.jj.templateproject.core.framework.presentation.viewmodels.states.BaseViewState
import com.jj.templateproject.monitoring.data.server.server.ServerManager
import com.jj.templateproject.monitoring.framework.viewmodels.ServerStatusViewModel.ViewAction
import com.jj.templateproject.monitoring.framework.viewmodels.ServerStatusViewModel.ViewState

class ServerStatusViewModel(private val serverManager: ServerManager) : BaseViewModel<ViewState, ViewAction>(ViewState()) {

    data class ViewState(val isLoading: Boolean = false) : BaseViewState

    sealed class ViewAction : BaseViewAction

    fun startServer() {
        serverManager.startServer(5000)
    }
}