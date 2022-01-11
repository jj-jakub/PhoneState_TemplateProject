package com.jj.templateproject.dictionary.framework.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jj.templateproject.core.framework.presentation.fragments.BaseFragment
import com.jj.templateproject.dictionary.R
import com.jj.templateproject.dictionary.databinding.FragmentDictionaryBinding
import com.jj.templateproject.dictionary.framework.viewmodels.DictionaryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DictionaryFragment : BaseFragment(R.layout.fragment_dictionary) {

    private val dictionaryViewModel: DictionaryViewModel by viewModel()

    private lateinit var fragmentDictionaryBinding: FragmentDictionaryBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentDictionaryBinding.inflate(inflater, container, false).let { binding ->
            fragmentDictionaryBinding = binding
            binding.root
        }

    override fun setupSubscriptions() {
        /* no-op */
    }
}