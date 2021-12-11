package com.jj.templateproject.core.data.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope

class CoroutineScopeProvider: com.jj.templateproject.core.data.coroutines.ICoroutineScopeProvider {

    @DelicateCoroutinesApi
    override fun createGlobalScope() = GlobalScope

    override fun createIOScope() = CoroutineScope(Dispatchers.IO)
    override fun createMainScope() = CoroutineScope(Dispatchers.Main)
}