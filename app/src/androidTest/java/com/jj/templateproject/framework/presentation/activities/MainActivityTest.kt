package com.jj.templateproject.framework.presentation.activities

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jj.templateproject.R
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
        onView(withId(R.id.mainLayout)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldShowProperInfoInMainLabel() {
        val expectedText = VersionTextProvider().getAboutVersionText()
        onView(withId(R.id.mainLabel)).check(matches(withText(expectedText)))
    }
}