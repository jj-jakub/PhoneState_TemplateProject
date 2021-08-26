package com.jj.templateproject.framework.presentation.activities

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jj.templateproject.R
import com.jj.templateproject.assertViewIsDisplayed
import com.jj.templateproject.assertViewIsNotDisplayed
import com.jj.templateproject.assertViewTextMatches
import com.jj.templateproject.data.text.VersionTextProvider
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

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
    fun gpsRelatedViewsShouldNOTBeVisible() {
        assertViewIsNotDisplayed(R.id.gpsStateIcon)
        assertViewIsNotDisplayed(R.id.gpsStateLabel)
        assertViewIsNotDisplayed(R.id.gpsStateValue)
    }
}