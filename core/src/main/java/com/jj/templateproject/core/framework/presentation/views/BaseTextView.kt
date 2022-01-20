package com.jj.templateproject.core.framework.presentation.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.jj.templateproject.core.R

class BaseTextView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = R.attr.autoCompleteTextViewStyle
) : AppCompatTextView(context, attrs, defStyleAttr)