package com.jj.templateproject

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matchers.not

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