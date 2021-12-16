package com.jj.templateproject.framework.presentation.activities.mainactivity

import android.graphics.Color
import androidx.test.core.app.ActivityScenario
import com.jj.templateproject.R
import com.jj.templateproject.core.domain.airplanemode.AirplaneModeManager
import com.jj.templateproject.core.domain.airplanemode.AirplaneModeState
import com.jj.templateproject.di.koin.mainModule
import com.jj.templateproject.framework.presentation.activities.MainActivity
import com.jj.templateproject.utils.DELAY_AFTER_CHANGE_EMIT
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

class MainActivityAirplaneModeChangesTest : KoinTest {

    @RelaxedMockK
    private lateinit var airplaneModeManager: AirplaneModeManager

    private lateinit var airplaneModeStateFlow: MutableStateFlow<AirplaneModeState>

    private lateinit var mockModule: Module

    private lateinit var rule: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        setupAirplaneModeManager()
        setupKoinModule()
        launchMainActivity()
    }

    private fun setupAirplaneModeManager() {
        airplaneModeStateFlow = MutableStateFlow(AirplaneModeState.Unknown)
        every { airplaneModeManager.observeAirplaneModeState() } returns airplaneModeStateFlow.asStateFlow()
    }

    private fun setupKoinModule() {
        mockModule = module {
            single { airplaneModeManager }
        }
        loadKoinModules(listOf(mainModule, mockModule))
    }

    private fun launchMainActivity() {
        rule = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun checkViewsCorrectnessOnAirplaneModeStateOn() {
        changeAirplaneModeStateFlow(AirplaneModeState.TurnedOn)

        assertViewTextMatches(R.id.airplaneModeStateLabel, R.string.airplane_mode_status)
        assertViewTextMatches(R.id.airplaneModeStateValue, R.string.active)
        assertBackgroundColorMatches(R.id.airplaneModeStateIcon, Color.GREEN)
        verify(exactly = 1) { airplaneModeManager.observeAirplaneModeState() }
    }

    @Test
    fun checkViewsCorrectnessOnAirplaneModeStateOff() {
        changeAirplaneModeStateFlow(AirplaneModeState.TurnedOff)

        assertViewTextMatches(R.id.airplaneModeStateLabel, R.string.airplane_mode_status)
        assertViewTextMatches(R.id.airplaneModeStateValue, R.string.not_active)
        assertBackgroundColorMatches(R.id.airplaneModeStateIcon, Color.RED)
        verify(exactly = 1) { airplaneModeManager.observeAirplaneModeState() }
    }

    @Test
    fun checkViewsCorrectnessOnAirplaneModeStateUnknown() {
        changeAirplaneModeStateFlow(AirplaneModeState.Unknown)

        assertViewTextMatches(R.id.airplaneModeStateLabel, R.string.airplane_mode_status)
        assertViewTextMatches(R.id.airplaneModeStateValue, R.string.unknown)
        assertBackgroundColorMatches(R.id.airplaneModeStateIcon, Color.GRAY)
        verify(exactly = 1) { airplaneModeManager.observeAirplaneModeState() }
    }

    private fun changeAirplaneModeStateFlow(airplaneModeState: AirplaneModeState) {
        airplaneModeStateFlow.value = airplaneModeState
        Thread.sleep(DELAY_AFTER_CHANGE_EMIT)
    }

    @After
    fun cleanup() {
        unloadKoinModules(listOf(mainModule, mockModule))
        rule.close()
    }
}