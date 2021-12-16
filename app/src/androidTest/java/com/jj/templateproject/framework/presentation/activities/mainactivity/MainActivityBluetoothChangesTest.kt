package com.jj.templateproject.framework.presentation.activities.mainactivity

import android.graphics.Color
import androidx.test.core.app.ActivityScenario
import com.jj.templateproject.R
import com.jj.templateproject.core.domain.bluetooth.BluetoothState
import com.jj.templateproject.core.domain.bluetooth.BluetoothStateManager
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

class MainActivityBluetoothChangesTest : KoinTest {

    @RelaxedMockK
    private lateinit var bluetoothStateManager: BluetoothStateManager

    private lateinit var bluetoothStateFlow: MutableStateFlow<BluetoothState>

    private lateinit var mockModule: Module

    private lateinit var rule: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        setupBluetoothStateManager()
        setupKoinModule()
        launchMainActivity()
    }

    private fun setupBluetoothStateManager() {
        bluetoothStateFlow = MutableStateFlow(BluetoothState.NotAvailable)
        every { bluetoothStateManager.observeBluetoothState() } returns bluetoothStateFlow.asStateFlow()
    }

    private fun setupKoinModule() {
        mockModule = module {
            single { bluetoothStateManager }
        }
        loadKoinModules(listOf(mainModule, mockModule))
    }

    private fun launchMainActivity() {
        rule = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun checkViewsCorrectnessOnBluetoothTurnedOn() {
        changeBluetoothStateFlow(BluetoothState.TurnedOn)

        assertViewTextMatches(R.id.bluetoothStateLabel, R.string.bluetooth_status)
        assertViewTextMatches(R.id.bluetoothStateValue, R.string.turned_on)
        assertBackgroundColorMatches(R.id.bluetoothStateIcon, Color.GREEN)
        verify(exactly = 1) { bluetoothStateManager.observeBluetoothState() }
    }

    @Test
    fun checkViewsCorrectnessOnBluetoothTurnedOff() {
        changeBluetoothStateFlow(BluetoothState.TurnedOff)

        assertViewTextMatches(R.id.bluetoothStateLabel, R.string.bluetooth_status)
        assertViewTextMatches(R.id.bluetoothStateValue, R.string.turned_off)
        assertBackgroundColorMatches(R.id.bluetoothStateIcon, Color.RED)
        verify(exactly = 1) { bluetoothStateManager.observeBluetoothState() }
    }

    @Test
    fun checkViewsCorrectnessOnBluetoothStateUnknown() {
        changeBluetoothStateFlow(BluetoothState.Unknown)

        assertViewTextMatches(R.id.bluetoothStateLabel, R.string.bluetooth_status)
        assertViewTextMatches(R.id.bluetoothStateValue, R.string.unknown)
        assertBackgroundColorMatches(R.id.bluetoothStateIcon, Color.GRAY)
        verify(exactly = 1) { bluetoothStateManager.observeBluetoothState() }
    }

    @Test
    fun checkViewsCorrectnessOnBluetoothTurningOn() {
        changeBluetoothStateFlow(BluetoothState.TurningOn)

        assertViewTextMatches(R.id.bluetoothStateLabel, R.string.bluetooth_status)
        assertViewTextMatches(R.id.bluetoothStateValue, R.string.turning_on)
        assertBackgroundColorMatches(R.id.bluetoothStateIcon, Color.RED)
        verify(exactly = 1) { bluetoothStateManager.observeBluetoothState() }
    }

    @Test
    fun checkViewsCorrectnessOnBluetoothTurningOff() {
        changeBluetoothStateFlow(BluetoothState.TurningOff)

        assertViewTextMatches(R.id.bluetoothStateLabel, R.string.bluetooth_status)
        assertViewTextMatches(R.id.bluetoothStateValue, R.string.turning_off)
        assertBackgroundColorMatches(R.id.bluetoothStateIcon, Color.RED)
        verify(exactly = 1) { bluetoothStateManager.observeBluetoothState() }
    }

    private fun changeBluetoothStateFlow(bluetoothState: BluetoothState) {
        bluetoothStateFlow.value = bluetoothState
        Thread.sleep(DELAY_AFTER_CHANGE_EMIT)
    }

    @After
    fun cleanup() {
        unloadKoinModules(listOf(mainModule, mockModule))
        rule.close()
    }
}