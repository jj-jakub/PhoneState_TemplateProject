package com.jj.templateproject.dictionary.framework.presentation.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jj.templateproject.core.framework.presentation.fragments.BaseFragment
import com.jj.templateproject.dictionary.R
import com.jj.templateproject.dictionary.databinding.FragmentDictionaryBinding
import com.jj.templateproject.dictionary.framework.presentation.adapters.WordListAdapter
import com.jj.templateproject.dictionary.framework.viewmodels.DictionaryViewModel
import com.jj.templateproject.dictionary.framework.viewmodels.DictionaryViewModel.UIEvent.ShowSnackbar
import com.jj.templateproject.dictionary.framework.viewmodels.DictionaryViewModel.ViewState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DictionaryFragment : BaseFragment(R.layout.fragment_dictionary) {

    private val dictionaryViewModel: DictionaryViewModel by viewModel()

    private lateinit var fragmentDictionaryBinding: FragmentDictionaryBinding

    private lateinit var wordListAdapter: WordListAdapter

    private val searchFieldTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            /* no-op */
        }

        override fun afterTextChanged(s: Editable?) {
            /* no-op */
        }

        override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
            dictionaryViewModel.onSearch(text.toString())
            /* no-op */
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentDictionaryBinding.inflate(inflater, container, false).let { binding ->
            fragmentDictionaryBinding = binding
            binding.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecycler()
        setupSearchField()
        setupRefreshSwipe()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupRecycler() {
        val recyclerDividerDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        AppCompatResources.getDrawable(requireContext(), R.drawable.word_results_divider)?.let { recyclerDividerDecoration.setDrawable(it) }
        wordListAdapter = WordListAdapter()

        with(fragmentDictionaryBinding.wordList) {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(recyclerDividerDecoration)
            adapter = wordListAdapter
        }
    }

    private fun setupSearchField() {
        with(fragmentDictionaryBinding.searchField) {
            addTextChangedListener(searchFieldTextWatcher)
            setOnEditorActionListener { view, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    dictionaryViewModel.onSearch(view.text.toString())
                }
                true
            }
        }
    }

    private fun setupRefreshSwipe() {
        with(fragmentDictionaryBinding.wordListParent) {
            setOnRefreshListener {
                val query = fragmentDictionaryBinding.searchField.text.toString()
                dictionaryViewModel.onSearch(query)
            }
            setColorSchemeResources(R.color.word_section_header, R.color.yellow)
        }
    }

    override fun setupSubscriptions() {
        dictionaryViewModel.stateLiveData.observe(viewLifecycleOwner) { state ->
            setLoadingPopupVisibility(isVisible = state.isLoading)
            fragmentDictionaryBinding.wordListParent.isRefreshing = state.isLoading

            handleWordList(state)
        }

        lifecycleScope.launch {
            dictionaryViewModel.eventFlow.collectLatest {
                when (it) {
                    is ShowSnackbar -> Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun handleWordList(state: ViewState?) {
        state?.wordInfoItems?.let { items -> wordListAdapter.setItems(items) }
        wordListAdapter.notifyDataSetChanged()
    }
}