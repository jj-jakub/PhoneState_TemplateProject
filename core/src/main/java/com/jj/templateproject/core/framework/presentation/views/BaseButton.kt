package com.jj.templateproject.core.framework.presentation.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import com.jj.templateproject.core.R

class BaseButton @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = R.attr.buttonStyle
) : AppCompatButton(context, attrs, defStyleAttr)