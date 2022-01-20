package com.jj.templateproject.notes.presentation.utils

import android.graphics.drawable.GradientDrawable
import androidx.annotation.ColorRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton

fun AppCompatButton.setBackgroundColorResource(@ColorRes colorRes: Int) = try {
    (background as GradientDrawable).color = AppCompatResources.getColorStateList(context, colorRes)
} catch (e: Exception) {
    e.printStackTrace()
}
