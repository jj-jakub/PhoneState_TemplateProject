package com.jj.templateproject.framework.viewmodels

import androidx.lifecycle.ViewModel
import com.jj.templateproject.data.coroutines.ICoroutineScopeProvider
import com.jj.templateproject.domain.network.NetworkManager
import com.jj.templateproject.domain.network.NetworkState
import com.jj.templateproject.domain.network.NetworkState.Connected
import com.jj.templateproject.domain.network.NetworkState.NotConnected
import com.jj.templateproject.domain.network.NetworkState.Unknown
import com.jj.templateproject.framework.viewmodels.states.MainViewState
import com.jj.templateproject.framework.viewmodels.states.NetworkViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class MainViewModel : ViewModel() {

    private val mainViewStateFlow = MutableStateFlow(MainViewState())

    fun observeMainViewState() = mainViewStateFlow.asStateFlow()

    private val networkManager: NetworkManager by inject(NetworkManager::class.java)
    private val coroutineScopeProvider: ICoroutineScopeProvider by inject(ICoroutineScopeProvider::class.java)

    init {
        coroutineScopeProvider.createIOScope().launch {
            networkManager.observeNetworkState().collect { onNetworkStateChanged(it) }
        }
    }

    private fun onNetworkStateChanged(networkState: NetworkState) {
        val networkViewState = when (networkState) {
            is Connected -> NetworkViewState(isKnown = true, isActive = true, type = networkState.type)
            is NotConnected -> NetworkViewState(isKnown = true, isActive = false)
            is Unknown -> NetworkViewState(isKnown = false)
        }

        val mainViewState = mainViewStateFlow.value.copy(networkViewState = networkViewState)
        changeMainViewStateFlow(mainViewState)
    }

    private fun changeMainViewStateFlow(mainViewState: MainViewState) {
        mainViewStateFlow.value = mainViewState
    }
}