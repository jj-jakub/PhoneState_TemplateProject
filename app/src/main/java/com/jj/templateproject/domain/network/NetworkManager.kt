package com.jj.templateproject.domain.network

import com.jj.templateproject.framework.network.NetworkState
import kotlinx.coroutines.flow.StateFlow

interface NetworkManager {

    fun observeNetworkState(): StateFlow<NetworkState>
}