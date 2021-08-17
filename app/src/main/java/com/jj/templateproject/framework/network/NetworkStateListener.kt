package com.jj.templateproject.framework.network

import android.net.ConnectivityManager
import android.net.Network
import com.jj.templateproject.data.network.NetworkChange
import com.jj.templateproject.domain.network.NetworkManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.java.KoinJavaComponent.inject

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