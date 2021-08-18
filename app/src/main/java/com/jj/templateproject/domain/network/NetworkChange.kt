package com.jj.templateproject.domain.network

import android.net.Network

sealed class NetworkChange {
    class NetworkAvailable(val network: Network): NetworkChange()
    class NetworkLosing(val info: String): NetworkChange()
    class NetworkLost(val network: Network): NetworkChange()
    object NetworkUnavailable: NetworkChange()
    object Unknown: NetworkChange()
}