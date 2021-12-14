package com.jj.templateproject.networking.framework.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jj.templateproject.core.framework.presentation.fragments.BaseFragment
import com.jj.templateproject.networking.R
import com.jj.templateproject.networking.databinding.FragmentApiResultsBinding
import com.jj.templateproject.networking.framework.viewmodels.ApiResultsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ApiResultsFragment : BaseFragment(R.layout.fragment_api_results) {

    private val apiResultsViewModel: ApiResultsViewModel by viewModel()

    private var fragmentApiResultsBinding: FragmentApiResultsBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentApiResultsBinding.inflate(inflater, container, false).let { binding ->
            fragmentApiResultsBinding = binding
            binding.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        apiResultsViewModel.fetchSpecies()
    }

    override fun setupSubscriptions() {
        apiResultsViewModel.stateLiveData.observe(viewLifecycleOwner) { state ->
            if (state.loadingSpecies) {
                fragmentApiResultsBinding?.loadingStateLabel?.text = "Loading"
            } else {
                fragmentApiResultsBinding?.loadingStateLabel?.text = "Finished loading"
            }
        }
    }
}