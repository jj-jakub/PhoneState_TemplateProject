package com.jj.templateproject.qrscanner.framework.viewmodels

import com.jj.templateproject.core.framework.presentation.viewmodels.BaseViewAction
import com.jj.templateproject.core.framework.presentation.viewmodels.BaseViewModel
import com.jj.templateproject.core.framework.presentation.viewmodels.states.BaseViewState
import com.jj.templateproject.qrscanner.framework.viewmodels.QrScannerViewModel.ViewAction
import com.jj.templateproject.qrscanner.framework.viewmodels.QrScannerViewModel.ViewState

class QrScannerViewModel : BaseViewModel<ViewState, ViewAction>(ViewState()) {

    data class ViewState(
            val isScanning: Boolean = false
    ) : BaseViewState

    sealed class ViewAction : BaseViewAction
}