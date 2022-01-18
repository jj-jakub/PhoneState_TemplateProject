package com.jj.templateproject.notes.featurenote.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jj.templateproject.core.framework.presentation.fragments.BaseFragment
import com.jj.templateproject.notes.R
import com.jj.templateproject.notes.databinding.FragmentNotesMainBinding
import com.jj.templateproject.notes.featurenote.presentation.adapters.NoteAdapter
import com.jj.templateproject.notes.featurenote.presentation.viewmodels.NotesMainViewModel

class NotesMainFragment : BaseFragment(R.layout.fragment_notes_main) {

    private val notesMainViewModel: NotesMainViewModel by viewModels()

    private lateinit var fragmentNotesMainBinding: FragmentNotesMainBinding

    private lateinit var adapter: NoteAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentNotesMainBinding.inflate(inflater, container, false).let { binding ->
            fragmentNotesMainBinding = binding
            binding.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
    }

    private fun setupRecycler() {
        val stateListRecycler = fragmentNotesMainBinding.notesRecycler
        stateListRecycler.layoutManager = LinearLayoutManager(requireContext())
        adapter = NoteAdapter()
        stateListRecycler.adapter = adapter
    }

    override fun setupSubscriptions() {
//        TODO("Not yet implemented")
    }
}