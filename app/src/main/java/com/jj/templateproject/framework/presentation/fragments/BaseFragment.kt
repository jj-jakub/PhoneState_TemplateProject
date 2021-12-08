package com.jj.templateproject.framework.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseFragment(layoutResource: Int) : Fragment(layoutResource) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSubscriptions()
    }

    abstract fun setupSubscriptions()
}