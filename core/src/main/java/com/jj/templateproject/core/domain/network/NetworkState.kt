package com.jj.templateproject.core.domain.network

sealed class NetworkState {
    class Connected(val type: String) : NetworkState()
    object NotConnected : NetworkState()
    object Unknown : NetworkState()
}