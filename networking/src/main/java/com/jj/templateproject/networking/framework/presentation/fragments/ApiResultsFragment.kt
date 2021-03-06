package com.jj.templateproject.networking.framework.presentation.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jj.templateproject.core.framework.presentation.fragments.BaseFragment
import com.jj.templateproject.networking.R
import com.jj.templateproject.networking.databinding.FragmentApiResultsBinding
import com.jj.templateproject.networking.framework.presentation.adapters.FishResultsListAdapter
import com.jj.templateproject.networking.framework.viewmodels.ApiResultsViewModel
import com.jj.templateproject.networking.framework.viewmodels.ApiResultsViewModel.ViewState
import org.koin.androidx.viewmodel.ext.android.viewModel

class ApiResultsFragment : BaseFragment(R.layout.fragment_api_results) {

    private val apiResultsViewModel: ApiResultsViewModel by viewModel()

    private lateinit var fragmentApiResultsBinding: FragmentApiResultsBinding

    private lateinit var fishResultsListAdapter: FishResultsListAdapter

    private val searchFieldTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            /* no-op */
        }

        override fun afterTextChanged(s: Editable?) {
            /* no-op */
        }

        override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
            apiResultsViewModel.filterSpeciesByName(text.toString())
            /* no-op */
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentApiResultsBinding.inflate(inflater, container, false).let { binding ->
            fragmentApiResultsBinding = binding
            binding.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecycler()
        setupRefreshSwipe()
        setupSearchField()
        super.onViewCreated(view, savedInstanceState)
        apiResultsViewModel.fetchSpecies()
    }

    private fun setupSearchField() {
        with(fragmentApiResultsBinding.searchField) {
            addTextChangedListener(searchFieldTextWatcher)
            setOnEditorActionListener { view, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    apiResultsViewModel.filterSpeciesByName(view.text.toString())
                }
                true
            }
        }
    }

    private fun setupRefreshSwipe() {
        with(fragmentApiResultsBinding.fishDetailsListParent) {
            setOnRefreshListener {
                apiResultsViewModel.fetchSpecies()
            }
            setColorSchemeResources(R.color.fish_section_header, R.color.yellow)
        }
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
            setLoadingPopupVisibility(isVisible = state.loadingSpecies)
            fragmentApiResultsBinding.fishDetailsListParent.isRefreshing = state.loadingSpecies

            handleSpeciesList(state)

            state.loadingError?.handle { errorMessage ->
                setErrorMessage(errorMessage)
            }
        }
    }

    private fun handleSpeciesList(state: ViewState) {
        if (state.loadingSpecies) {
            setNormalMessage("Loading")
            fishResultsListAdapter.setItems(listOf())
        } else {
            setNormalMessage("Finished loading")
            fishResultsListAdapter.setItems(state.fishItemsList)
        }
        fishResultsListAdapter.notifyDataSetChanged()
    }

    private fun setErrorMessage(message: String) {
        with(fragmentApiResultsBinding.loadingStateLabel) {
            setTextAppearance(R.style.ErrorText)
            text = message
        }
    }

    private fun setNormalMessage(message: String) {
        with(fragmentApiResultsBinding.loadingStateLabel) {
            setTextAppearance(R.style.CasualText)
            text = message
        }
    }
}