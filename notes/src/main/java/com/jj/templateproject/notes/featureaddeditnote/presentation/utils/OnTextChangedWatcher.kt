package com.jj.templateproject.notes.featureaddeditnote.presentation.utils

import android.text.Editable
import android.text.TextWatcher

class OnTextChangedWatcher(private val onTextChanged: (String) -> Unit): TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        /* no-op */
    }

    override fun onTextChanged(charSequence: CharSequence, p1: Int, p2: Int, p3: Int) {
        onTextChanged.invoke(charSequence.toString())
    }

    override fun afterTextChanged(p0: Editable?) {
        /* no-op */
    }
}