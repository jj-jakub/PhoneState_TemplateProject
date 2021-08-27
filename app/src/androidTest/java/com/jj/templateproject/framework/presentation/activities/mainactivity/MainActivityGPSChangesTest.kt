package com.jj.templateproject.framework.presentation.activities.mainactivity

import android.graphics.Color
import androidx.test.core.app.ActivityScenario
import com.jj.templateproject.R
import com.jj.templateproject.di.koin.mainModule
import com.jj.templateproject.domain.bluetooth.BluetoothState
import com.jj.templateproject.domain.gps.GPSManager
import com.jj.templateproject.domain.gps.GPSState
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

class MainActivityGPSChangesTest : KoinTest {

    @RelaxedMockK
    private lateinit var gpsManager: GPSManager

    private lateinit var gpsStateFlow: MutableStateFlow<GPSState>

    private lateinit var mockModule: Module

    private lateinit var rule: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        setupGPSManager()
        setupKoinModule()
        launchMainActivity()
    }

    private fun setupGPSManager() {
        gpsStateFlow = MutableStateFlow(GPSState.Unknown)
        every { gpsManager.observeGPSState() } returns gpsStateFlow.asStateFlow()
    }

    private fun setupKoinModule() {
        mockModule = module {
            single { gpsManager }
        }
        loadKoinModules(listOf(mainModule, mockModule))
    }

    private fun launchMainActivity() {
        rule = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun checkViewsCorrectnessOnGPSStateOn() {
        changeGPSStateFlow(GPSState.TurnedOn)

        assertViewTextMatches(R.id.gpsStateLabel, R.string.gps_status)
        assertViewTextMatches(R.id.gpsStateValue, R.string.active)
        assertBackgroundColorMatches(R.id.gpsStateIcon, Color.GREEN)
        verify(exactly = 1) { gpsManager.observeGPSState() }
    }

    @Test
    fun checkViewsCorrectnessOnGPSStateOff() {
        changeGPSStateFlow(GPSState.TurnedOff)

        assertViewTextMatches(R.id.gpsStateLabel, R.string.gps_status)
        assertViewTextMatches(R.id.gpsStateValue, R.string.not_active)
        assertBackgroundColorMatches(R.id.gpsStateIcon, Color.RED)
        verify(exactly = 1) { gpsManager.observeGPSState() }
    }

    @Test
    fun checkViewsCorrectnessOnGPSStateUnknown() {
        changeGPSStateFlow(GPSState.Unknown)

        assertViewTextMatches(R.id.gpsStateLabel, R.string.gps_status)
        assertViewTextMatches(R.id.gpsStateValue, R.string.unknown)
        assertBackgroundColorMatches(R.id.gpsStateIcon, Color.GRAY)
        verify(exactly = 1) { gpsManager.observeGPSState() }
    }

    private fun changeGPSStateFlow(gpsState: GPSState) {
        gpsStateFlow.value = gpsState
        Thread.sleep(DELAY_AFTER_CHANGE_EMIT)
    }

    @After
    fun cleanup() {
        unloadKoinModules(listOf(mainModule, mockModule))
        rule.close()
    }
}