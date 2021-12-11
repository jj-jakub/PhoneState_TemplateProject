package com.jj.templateproject.networking.framework.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.jj.templateproject.core.framework.presentation.fragments.BaseFragment
import com.jj.templateproject.networking.R
import com.jj.templateproject.networking.databinding.FragmentApiResultsBinding
import com.jj.templateproject.networking.framework.viewmodels.ApiResultsViewModel

class ApiResultsFragment: BaseFragment(R.layout.fragment_api_results) {

    private val apiResultsViewModel: ApiResultsViewModel by viewModels()

    private var fragmentApiResultsBinding: FragmentApiResultsBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentApiResultsBinding.inflate(inflater, container, false).let { binding ->
            fragmentApiResultsBinding = binding
            binding.root
        }

    override fun setupSubscriptions() {
//        TODO("Not yet implemented")
    }
}