package com.jj.templateproject.networking.utils

import com.jj.templateproject.core.data.coroutines.ICoroutineScopeProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope

@ExperimentalCoroutinesApi
class TestCoroutineScopeProvider : ICoroutineScopeProvider {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    override fun createGlobalScope(): TestCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)

    override fun createIOScope(): TestCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)

    override fun createMainScope(): TestCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)
}