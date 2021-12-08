package com.jj.templateproject.framework.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jj.templateproject.R
import com.jj.templateproject.databinding.FragmentStatesListBinding
import com.jj.templateproject.framework.presentation.adapters.StateListAdapter
import com.jj.templateproject.framework.viewmodels.StatesListViewModel

class StatesListFragment : BaseFragment(R.layout.fragment_states_list) {

    private val statesListViewModel: StatesListViewModel by viewModels()

    private lateinit var fragmentStatesListBinding: FragmentStatesListBinding

    private lateinit var adapter: StateListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentStatesListBinding.inflate(inflater, container, false).let { binding ->
            fragmentStatesListBinding = binding
            binding.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecycler()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupRecycler() {
        val stateListRecycler = fragmentStatesListBinding.statesList
        stateListRecycler.layoutManager = LinearLayoutManager(requireContext())
        adapter = StateListAdapter()
        stateListRecycler.adapter = adapter
    }

    override fun setupSubscriptions() {
        val items = statesListViewModel.getListItems()
        adapter.setItems(items)
        adapter.notifyDataSetChanged()
    }
}