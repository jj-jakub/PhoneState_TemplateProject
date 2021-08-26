package com.jj.templateproject.utils

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

fun matchesBackgroundColor(expectedColor: Int): Matcher<View?> =
    object : BoundedMatcher<View?, View>(View::class.java) {
        var actualColor = 0
        var message: String? = null

        override fun matchesSafely(item: View): Boolean {
            if (item.background == null) {
                message = item.id.toString() + " does not have a background"
                return false
            }
            actualColor = try {
                (item.background as ColorDrawable).color
            } catch (e: Exception) {
                (item.background as GradientDrawable).color!!.defaultColor
            }
            return actualColor == expectedColor
        }

        override fun describeTo(description: Description) {
            if (actualColor != 0) {
                message = ("Background color did not match: Expected "
                        + String.format(
                    "#%06X",
                    0xFFFFFF and expectedColor
                ) + " was " + String.format(
                    "#%06X",
                    0xFFFFFF and actualColor
                ))
            }
            description.appendText(message)
        }
    }