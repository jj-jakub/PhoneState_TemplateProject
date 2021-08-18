package com.jj.templateproject.framework.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities.TRANSPORT_BLUETOOTH
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import com.jj.templateproject.data.coroutines.ICoroutineScopeProvider
import com.jj.templateproject.domain.network.NetworkChange
import com.jj.templateproject.domain.network.NetworkChange.NetworkAvailable
import com.jj.templateproject.domain.network.NetworkChange.NetworkLost
import com.jj.templateproject.domain.network.NetworkManager
import com.jj.templateproject.domain.network.NetworkState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AndroidNetworkManager(
    private val context: Context,
    private val networkStateListener: NetworkStateListener,
    private val coroutineScopeProvider: ICoroutineScopeProvider
) : NetworkManager {

    private val networkStateFlow = MutableStateFlow<NetworkState>(NetworkState.Unknown)

    override fun observeNetworkState() = networkStateFlow.asStateFlow()

    init {
        checkInitialInternetConnection()
        registerNetworkCallback()
        observeNetworkChanges()
    }

    private fun checkInitialInternetConnection() {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetwork = connectivityManager?.activeNetwork ?: return onNoActiveNetwork()
        val transport = getNetworkTransport(activeNetwork)
        changeNetworkStateFlow(NetworkState.Connected("Transport: $transport"))
    }

    private fun registerNetworkCallback() {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        connectivityManager?.registerDefaultNetworkCallback(networkStateListener)
    }

    private fun observeNetworkChanges() {
        coroutineScopeProvider.createIOScope().launch {
            networkStateListener.observeNetworkChangeFlow().collect {
                onNetworkStateChanged(it)
            }
        }
    }

    private fun getNetworkTransport(activeNetwork: Network): String {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val capabilities = connectivityManager?.getNetworkCapabilities(activeNetwork)
        return when {
            capabilities == null -> "Unknown transport"
            capabilities.hasTransport(TRANSPORT_WIFI) -> "WIFI"
            capabilities.hasTransport(TRANSPORT_CELLULAR) -> "CELLULAR"
            capabilities.hasTransport(TRANSPORT_ETHERNET) -> "ETHERNET"
            capabilities.hasTransport(TRANSPORT_BLUETOOTH) -> "BLUETOOTH"
            else -> "Unknown transport"
        }
    }

    private fun onNetworkStateChanged(networkChange: NetworkChange) {
        val networkState = when (networkChange) {
            is NetworkAvailable -> NetworkState.Connected(getNetworkTransport(networkChange.network))
            is NetworkLost -> NetworkState.NotConnected
            else -> return
        }

        changeNetworkStateFlow(networkState)
    }

    private fun onNoActiveNetwork() {
        changeNetworkStateFlow(NetworkState.NotConnected)
    }

    private fun changeNetworkStateFlow(state: NetworkState) {
        networkStateFlow.value = state
    }
}