package com.jj.templateproject.core.framework.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseFragment(layoutResource: Int) : Fragment(layoutResource) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSubscriptions()
    }

    protected abstract fun setupSubscriptions()

    protected fun setLoadingPopupVisibility(isVisible: Boolean) {

    }
}