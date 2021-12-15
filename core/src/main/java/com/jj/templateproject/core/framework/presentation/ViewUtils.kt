package com.jj.templateproject.core.framework.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

inline fun <T : ViewBinding> ViewGroup.createBinding(
        bindingFactory: (inflater: LayoutInflater, parent: ViewGroup, attach: Boolean) -> T) =
    bindingFactory(LayoutInflater.from(context), this, false)