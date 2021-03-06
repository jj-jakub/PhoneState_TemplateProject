package com.jj.templateproject.core.framework.network

import android.net.ConnectivityManager
import android.net.Network
import com.jj.templateproject.core.domain.network.NetworkChange
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NetworkStateListener : ConnectivityManager.NetworkCallback() {

    private val networkChangeFlow = MutableStateFlow<NetworkChange>(NetworkChange.Unknown)

    fun observeNetworkChangeFlow() = networkChangeFlow.asStateFlow()

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        networkChangeFlow.value = NetworkChange.NetworkAvailable(network)
    }

    override fun onLosing(network: Network, maxMsToLive: Int) {
        super.onLosing(network, maxMsToLive)
        networkChangeFlow.value = NetworkChange.NetworkLosing("maxMsToLive: $maxMsToLive")
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        networkChangeFlow.value = NetworkChange.NetworkLost(network)
    }

    override fun onUnavailable() {
        super.onUnavailable()
        networkChangeFlow.value = NetworkChange.NetworkUnavailable
    }
}