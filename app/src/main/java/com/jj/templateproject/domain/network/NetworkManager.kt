package com.jj.templateproject.domain.network

import kotlinx.coroutines.flow.StateFlow

interface NetworkManager {

    fun observeNetworkState(): StateFlow<NetworkState>
}