package com.jj.templateproject.framework.presentation.activities.mainactivity

import android.graphics.Color
import androidx.test.core.app.ActivityScenario
import com.jj.templateproject.R
import com.jj.templateproject.di.koin.mainModule
import com.jj.templateproject.domain.network.NetworkManager
import com.jj.templateproject.domain.network.NetworkState
import com.jj.templateproject.framework.presentation.activities.MainActivity
import com.jj.templateproject.utils.assertBackgroundColorMatches
import com.jj.templateproject.utils.assertViewTextMatches
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.test.KoinTest

class MainActivityNetworkChangesTest : KoinTest {

    @RelaxedMockK
    private lateinit var networkManager: NetworkManager

    private lateinit var networkStateFlow: MutableStateFlow<NetworkState>

    private lateinit var mockModule: Module

    private lateinit var rule: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        setupNetworkManager()
        setupKoinModule()
        launchMainActivity()
    }

    private fun setupNetworkManager() {
        networkStateFlow = MutableStateFlow(NetworkState.Unknown)
        every { networkManager.observeNetworkState() } returns networkStateFlow.asStateFlow()
    }

    private fun setupKoinModule() {
        mockModule = module {
            single { networkManager }
        }
        loadKoinModules(listOf(mainModule, mockModule))
    }

    private fun launchMainActivity() {
        rule = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun checkViewsCorrectnessOnWiFiStateOn() {
        val networkName = "MockNetworkName"
        networkStateFlow.value = NetworkState.Connected(networkName)

        assertViewTextMatches(R.id.networkStateLabel, R.string.network_status)
        assertViewTextMatches(R.id.networkStateValue, networkName)
        assertBackgroundColorMatches(R.id.networkStateIcon, Color.GREEN)
        verify(exactly = 1) { networkManager.observeNetworkState() }
    }

    @Test
    fun checkViewsCorrectnessOnWiFiStateOff() {
        networkStateFlow.value = NetworkState.NotConnected

        assertViewTextMatches(R.id.networkStateLabel, R.string.network_status)
        assertViewTextMatches(R.id.networkStateValue, R.string.not_active)
        assertBackgroundColorMatches(R.id.networkStateIcon, Color.RED)
        verify(exactly = 1) { networkManager.observeNetworkState() }
    }

    @Test
    fun checkViewsCorrectnessOnWiFiStateUnknown() {
        networkStateFlow.value = NetworkState.Unknown

        assertViewTextMatches(R.id.networkStateLabel, R.string.network_status)
        assertViewTextMatches(R.id.networkStateValue, R.string.unknown)
        assertBackgroundColorMatches(R.id.networkStateIcon, Color.GRAY)
        verify(exactly = 1) { networkManager.observeNetworkState() }
    }

    @After
    fun cleanup() {
        unloadKoinModules(listOf(mainModule, mockModule))
        rule.close()
    }
}