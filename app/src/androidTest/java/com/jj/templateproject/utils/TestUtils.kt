package com.jj.templateproject.utils

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.Matchers.not

const val DELAY_AFTER_CHANGE_EMIT = 1500L

fun assertViewIsDisplayed(id: Int) {
    Espresso.onView(ViewMatchers.withId(id))
        .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
}

fun assertViewIsNotDisplayed(id: Int) {
    Espresso.onView(ViewMatchers.withId(id))
        .check(ViewAssertions.matches(not(ViewMatchers.isDisplayed())))
}

fun assertViewTextMatches(id: Int, text: String) {
    Espresso.onView(ViewMatchers.withId(id))
        .check(ViewAssertions.matches(ViewMatchers.withText(text)))
}

fun assertViewTextMatches(id: Int, textRes: Int) {
    Espresso.onView(ViewMatchers.withId(id))
        .check(ViewAssertions.matches(ViewMatchers.withText(getString(textRes))))
}

fun assertBackgroundColorMatches(id: Int, color: Int) {
    Espresso.onView(ViewMatchers.withId(id))
        .check(ViewAssertions.matches(matchesBackgroundColor(color)))
}

private fun getString(textRes: Int) =
    InstrumentationRegistry.getInstrumentation().targetContext.getString(textRes)