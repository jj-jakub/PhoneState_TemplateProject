package com.jj.templateproject.notes.featurenote.presentation.fragments

import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jj.templateproject.core.framework.presentation.fragments.BaseFragment
import com.jj.templateproject.notes.R
import com.jj.templateproject.notes.databinding.FragmentNotesMainBinding
import com.jj.templateproject.notes.featureaddeditnote.presentation.fragments.NOTE_TO_EDIT_ID
import com.jj.templateproject.notes.featurenote.presentation.adapters.NoteAdapter
import com.jj.templateproject.notes.featurenote.presentation.utils.NoteMainViewEvent
import com.jj.templateproject.notes.featurenote.presentation.viewmodels.NotesMainViewModel
import com.jj.templateproject.notes.navigation.NoteNavigation
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.java.KoinJavaComponent.inject
import kotlin.properties.Delegates

class NotesMainFragment : BaseFragment(R.layout.fragment_notes_main) {

    private val notesMainViewModel: NotesMainViewModel by viewModel()

    private val noteNavigation: NoteNavigation by inject(NoteNavigation::class.java)

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
        fragmentNotesMainBinding.undoDeleteButton.setOnClickListener {
            notesMainViewModel.onEvent(NoteMainViewEvent.RestoreNote)
        }
        fragmentNotesMainBinding.createNoteButton.setOnClickListener {
            openAddEditNoteScreen()
        }
        abcd()
    }

    private val DURATION = 750L
    var a = true

    private var initialViewHeight by Delegates.notNull<Int>()
    private var initialAscHeight by Delegates.notNull<Int>()
    private var initialDscHeight by Delegates.notNull<Int>()

    private fun abcd() {
        fragmentNotesMainBinding.orderSelectionSection.measure(0, 0)
        fragmentNotesMainBinding.orderSelectionSection.getAscBtn().measure(0, 0)
        fragmentNotesMainBinding.orderSelectionSection.getColorBtn().measure(0, 0)
        initialViewHeight = fragmentNotesMainBinding.orderSelectionSection.measuredHeight
        initialAscHeight = fragmentNotesMainBinding.orderSelectionSection.getAscBtn().measuredHeight
        initialDscHeight = fragmentNotesMainBinding.orderSelectionSection.getColorBtn().measuredHeight

//        val valueAnimator = ValueAnimator()
//        valueAnimator.setFloatValues(0f, 1000f)
//        valueAnimator.setTarget(fragmentNotesMainBinding.orderSelectionSection)
//        valueAnimator.addUpdateListener { animation ->
//            val step = animation.animatedValue as Int
//            fragmentNotesMainBinding.orderSelectionSection.layoutParams.height = (height * ((MAX_STEP_PERCENTAGE - step) / MAX_STEP_PERCENTAGE)).toInt()
//
//        }
//        valueAnimator.duration = DURATION
//
//        val animatorUpdateListener = ValueAnimator.AnimatorUpdateListener { TODO("Not yet implemented") }


        fragmentNotesMainBinding.helperButton.setOnClickListener {
            showView(fragmentNotesMainBinding.orderSelectionSection, initialViewHeight)
//            showViewInsideConstraintLayout(fragmentNotesMainBinding.orderSelectionSection.getAscBtn(), initialAscHeight)
//            showViewInsideConstraintLayout(fragmentNotesMainBinding.orderSelectionSection.getColorBtn(), initialDscHeight)
//            if (a) fragmentNotesMainBinding.orderSelectionSection.startAnimation(outAnim)
//            else fragmentNotesMainBinding.orderSelectionSection.startAnimation(inAnim)
//            a = !a
        }
        fragmentNotesMainBinding.helperButton2.setOnClickListener {
            hideView(fragmentNotesMainBinding.orderSelectionSection)
//            hideViewInsideConstraintLayout(fragmentNotesMainBinding.orderSelectionSection.getAscBtn())
//            hideViewInsideConstraintLayout(fragmentNotesMainBinding.orderSelectionSection.getColorBtn())
        }
    }

    private fun increaseViewSize(view: View, increaseValue: Int) {
        val valueAnimator =
            ValueAnimator.ofInt(view.measuredHeight, view.measuredHeight + increaseValue)
        valueAnimator.duration = DURATION
        valueAnimator.addUpdateListener {
            val animatedValue = valueAnimator.animatedValue as Int
            val layoutParams = view.layoutParams
            layoutParams.height = animatedValue
            view.layoutParams = layoutParams
        }
        valueAnimator.start()
    }

    private fun showView(view: View, init: Int) {
        val valueAnimator =
            ValueAnimator.ofInt(view.measuredHeight, init)
        valueAnimator.duration = DURATION
        valueAnimator.addUpdateListener {
            val animatedValue = valueAnimator.animatedValue as Int
            val layoutParams = view.layoutParams
            layoutParams.height = animatedValue
            view.layoutParams = layoutParams
            Log.d("ABAB", "show animVal: $animatedValue")
        }
        valueAnimator.start()
    }

    private fun showViewInsideConstraintLayout(view: View, init: Int) {
        val valueAnimator = ValueAnimator.ofInt(0, init)
        valueAnimator.duration = DURATION
        valueAnimator.addUpdateListener {
            val animatedValue = valueAnimator.animatedValue as Int
            val layoutParams = view.layoutParams
            layoutParams.height = animatedValue
            view.layoutParams = layoutParams
            if (animatedValue == 1) view.isVisible = true
        }
        valueAnimator.start()
    }

    private fun hideViewInsideConstraintLayout(view: View) {
        val valueAnimator = ValueAnimator.ofInt(view.measuredHeight, 0)
        valueAnimator.duration = DURATION
        valueAnimator.addUpdateListener {
            val animatedValue = valueAnimator.animatedValue as Int
            val layoutParams = view.layoutParams
            layoutParams.height = animatedValue
            view.layoutParams = layoutParams
            if (animatedValue == 0) view.visibility = View.INVISIBLE
        }
        valueAnimator.start()
    }

    private fun hideView(view: View) {
        val valueAnimator = ValueAnimator.ofInt(view.measuredHeight, 0)
        valueAnimator.duration = DURATION
        valueAnimator.addUpdateListener {
            val animatedValue = valueAnimator.animatedValue as Int
            val layoutParams = view.layoutParams
            layoutParams.height = animatedValue
            view.layoutParams = layoutParams
            Log.d("ABAB", "hide animVal: $animatedValue")
        }
        valueAnimator.start()
    }

    private fun decreaseViewSize(view: View, increaseValue: Int) {
        val valueAnimator =
            ValueAnimator.ofInt(view.measuredHeight, view.measuredHeight - increaseValue)
        valueAnimator.duration = 500L
        valueAnimator.addUpdateListener {
            val animatedValue = valueAnimator.animatedValue as Int
            val layoutParams = view.layoutParams
            layoutParams.height = animatedValue
            view.layoutParams = layoutParams
        }
        valueAnimator.start()
    }

    private fun setupOrderSelectionSection() {
        fragmentNotesMainBinding.orderSelectionSection.setOnOrderChangeListener {
            notesMainViewModel.onEvent(NoteMainViewEvent.OrderChanged(it))
        }
    }

    private fun setupRecycler() {
        val stateListRecycler = fragmentNotesMainBinding.notesRecycler
        stateListRecycler.layoutManager = LinearLayoutManager(requireContext())
        adapter = NoteAdapter(
                onItemClickListener = { openAddEditNoteScreen(it.id) },
                onDeleteClickListener = { notesMainViewModel.onEvent(NoteMainViewEvent.DeleteNote(it)) }
        )
        stateListRecycler.adapter = adapter
    }

    private fun openAddEditNoteScreen(noteId: Int? = null) {
        val bundle = bundleOf(NOTE_TO_EDIT_ID to noteId)
        findNavController().navigate(noteNavigation.addEditNoteDestinationId(), bundle)
    }

    override fun setupSubscriptions() {
        notesMainViewModel.stateLiveData.observe(viewLifecycleOwner) { state ->
            setLoadingPopupVisibility(state.isLoading)

            fragmentNotesMainBinding.undoDeleteButton.isEnabled = state.undoDeleteNotePossible
            fragmentNotesMainBinding.orderSelectionSection.setNoteOrder(state.noteOrder)
            adapter.setItems(state.notes)
            adapter.notifyDataSetChanged()
        }
    }
}