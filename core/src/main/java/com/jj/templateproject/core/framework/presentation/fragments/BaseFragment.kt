package com.jj.templateproject.core.framework.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.jj.templateproject.core.framework.presentation.dialogs.IndefiniteProgressDialog

private const val PROGRESS_DIALOG_TAG = "PROGRESS_DIALOG_TAG"

abstract class BaseFragment(layoutResource: Int) : Fragment(layoutResource) {

    private val progressDialog = IndefiniteProgressDialog()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSubscriptions()
    }

    protected abstract fun setupSubscriptions()

    protected fun setLoadingPopupVisibility(isVisible: Boolean) {
        if (isVisible && progressDialog.isAdded.not()) progressDialog.show(childFragmentManager, PROGRESS_DIALOG_TAG)
        else progressDialog.dismiss()
    }
}