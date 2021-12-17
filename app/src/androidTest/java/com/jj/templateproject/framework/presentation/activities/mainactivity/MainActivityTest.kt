package com.jj.templateproject.framework.presentation.activities.mainactivity

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jj.templateproject.R
import com.jj.templateproject.data.text.VersionTextProvider
import com.jj.templateproject.di.koin.mainModule
import com.jj.templateproject.framework.presentation.activities.MainActivity
import com.jj.templateproject.utils.assertViewIsDisplayed
import com.jj.templateproject.utils.assertViewTextMatches
import com.jj.templateproject.utils.di.defaultKoinModules
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    companion object {
        @BeforeClass
        @JvmStatic
        fun setup() {
            loadKoinModules(defaultKoinModules)
        }
    }

    @get:Rule
    var rule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun mainLayoutShouldBeVisible() {
        assertViewIsDisplayed(R.id.mainLayout)
    }

    @Test
    fun shouldShowProperInfoInMainLabel() {
        val expectedText = VersionTextProvider().getAboutVersionText()
        assertViewTextMatches(R.id.mainLabel, expectedText)
    }

    @Test
    fun networkRelatedViewsShouldBeVisible() {
        assertViewIsDisplayed(R.id.networkStateIcon)
        assertViewIsDisplayed(R.id.networkStateLabel)
        assertViewIsDisplayed(R.id.networkStateValue)
    }

    @Test
    fun bluetoothRelatedViewsShouldBeVisible() {
        assertViewIsDisplayed(R.id.bluetoothStateIcon)
        assertViewIsDisplayed(R.id.bluetoothStateLabel)
        assertViewIsDisplayed(R.id.bluetoothStateValue)
    }

    @Test
    fun airplaneModeRelatedViewsShouldBeVisible() {
        assertViewIsDisplayed(R.id.airplaneModeStateIcon)
        assertViewIsDisplayed(R.id.airplaneModeStateLabel)
        assertViewIsDisplayed(R.id.airplaneModeStateValue)
    }

    @Test
    fun gpsRelatedViewsShouldBeVisible() {
        assertViewIsDisplayed(R.id.gpsStateIcon)
        assertViewIsDisplayed(R.id.gpsStateLabel)
        assertViewIsDisplayed(R.id.gpsStateValue)
    }
}