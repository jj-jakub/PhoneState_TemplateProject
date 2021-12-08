package com.jj.templateproject.framework.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.jj.templateproject.R
import com.jj.templateproject.databinding.FragmentStatesListBinding
import com.jj.templateproject.framework.viewmodels.StatesListViewModel

class StatesListFragment: BaseFragment(R.layout.fragment_states_list) {

    private val statesListViewModel: StatesListViewModel by viewModels()

    private var fragmentStatesListBinding: FragmentStatesListBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentStatesListBinding.inflate(inflater, container, false).let { binding ->
            fragmentStatesListBinding = binding
            binding.root
        }

    override fun setupSubscriptions() {
        /* no-op */
    }
}