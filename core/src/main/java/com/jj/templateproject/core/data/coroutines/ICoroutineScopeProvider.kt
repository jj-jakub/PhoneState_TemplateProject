package com.jj.templateproject.core.data.coroutines

import kotlinx.coroutines.CoroutineScope

interface ICoroutineScopeProvider {

    fun createGlobalScope(): CoroutineScope
    fun createIOScope(): CoroutineScope
    fun createMainScope(): CoroutineScope
}