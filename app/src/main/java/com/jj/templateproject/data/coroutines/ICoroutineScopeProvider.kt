package com.jj.templateproject.data.coroutines

import kotlinx.coroutines.CoroutineScope

interface ICoroutineScopeProvider {

    fun createGlobalScope(): CoroutineScope
    fun createIOScope(): CoroutineScope
    fun createMainScope(): CoroutineScope
}