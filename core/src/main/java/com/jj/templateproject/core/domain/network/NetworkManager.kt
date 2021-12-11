package com.jj.templateproject.core.domain.network

import kotlinx.coroutines.flow.StateFlow

interface NetworkManager {

    fun observeNetworkState(): StateFlow<NetworkState>
}