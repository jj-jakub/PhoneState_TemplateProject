package com.jj.templateproject.notes.featurenote.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.jj.templateproject.core.framework.presentation.fragments.BaseFragment
import com.jj.templateproject.notes.R
import com.jj.templateproject.notes.databinding.FragmentNotesMainBinding
import com.jj.templateproject.notes.featurenote.presentation.adapters.NoteAdapter
import com.jj.templateproject.notes.featurenote.presentation.utils.NoteMainViewEvent
import com.jj.templateproject.notes.featurenote.presentation.utils.NoteViewData
import com.jj.templateproject.notes.featurenote.presentation.viewmodels.NotesMainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotesMainFragment : BaseFragment(R.layout.fragment_notes_main) {

    private val notesMainViewModel: NotesMainViewModel by viewModel()

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
        setupOrderSelectionSection()
    }

    private fun setupOrderSelectionSection() {
        fragmentNotesMainBinding.orderSelectionSection.setOnOrderChangeListener {
            notesMainViewModel.onEvent(NoteMainViewEvent.OrderChanged(it))
        }
    }

    private fun setupRecycler() {
        val stateListRecycler = fragmentNotesMainBinding.notesRecycler
        stateListRecycler.layoutManager = LinearLayoutManager(requireContext())
        adapter = NoteAdapter()
        stateListRecycler.adapter = adapter
    }

    override fun setupSubscriptions() {
        notesMainViewModel.stateLiveData.observe(viewLifecycleOwner) { state ->
            setLoadingPopupVisibility(state.isLoading)

            fragmentNotesMainBinding.orderSelectionSection.setNoteOrder(state.noteOrder)
            adapter.setItems(state.notes.map { NoteViewData(title = it.title, content = it.content, color = it.color) })
            adapter.notifyDataSetChanged()
        }
    }
}