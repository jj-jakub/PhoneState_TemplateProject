package com.jj.templateproject.networking.framework.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jj.templateproject.core.framework.presentation.fragments.BaseFragment
import com.jj.templateproject.networking.R
import com.jj.templateproject.networking.databinding.FragmentApiResultsBinding
import com.jj.templateproject.networking.framework.presentation.adapters.FishResultsListAdapter
import com.jj.templateproject.networking.framework.viewmodels.ApiResultsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ApiResultsFragment : BaseFragment(R.layout.fragment_api_results) {

    private val apiResultsViewModel: ApiResultsViewModel by viewModel()

    private lateinit var fragmentApiResultsBinding: FragmentApiResultsBinding

    private lateinit var fishResultsListAdapter: FishResultsListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentApiResultsBinding.inflate(inflater, container, false).let { binding ->
            fragmentApiResultsBinding = binding
            binding.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecycler()
        super.onViewCreated(view, savedInstanceState)
        apiResultsViewModel.fetchSpecies()
    }

    private fun setupRecycler() {
        val recyclerDividerDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        AppCompatResources.getDrawable(requireContext(), R.drawable.fish_results_divider)?.let { recyclerDividerDecoration.setDrawable(it) }
        fishResultsListAdapter = FishResultsListAdapter()

        with(fragmentApiResultsBinding.fishDetailsList) {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(recyclerDividerDecoration)
            adapter = fishResultsListAdapter
        }
    }

    override fun setupSubscriptions() {
        apiResultsViewModel.stateLiveData.observe(viewLifecycleOwner) { state ->
            if (state.loadingSpecies) {
                fragmentApiResultsBinding.loadingStateLabel.text = "Loading"
                fishResultsListAdapter.setItems(listOf())
            } else {
                fragmentApiResultsBinding.loadingStateLabel.text = "Finished loading"
                fishResultsListAdapter.setItems(state.fishItemsList)
            }
            fishResultsListAdapter.notifyDataSetChanged()
        }
    }
}