package com.jj.templateproject.monitoring.framework.presentation.fragments

import android.os.Bundle
import android.view.View
import com.jj.templateproject.core.framework.presentation.fragments.BaseFragment
import com.jj.templateproject.monitoring.R
import com.jj.templateproject.monitoring.data.server.server.ServerManager
import com.jj.templateproject.monitoring.framework.viewmodels.ServerStatusViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.java.KoinJavaComponent

class ServerStatusFragment : BaseFragment(R.layout.fragment_server_status) {

    private val serverStatusViewModel: ServerStatusViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        serverStatusViewModel.startServer()
    }

    override fun setupSubscriptions() {
        /* no-op */
    }
}