package com.jj.templateproject.notes.featureaddeditnote.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.jj.templateproject.core.framework.presentation.fragments.BaseFragment
import com.jj.templateproject.notes.R
import com.jj.templateproject.notes.databinding.FragmentAddEditNoteBinding
import com.jj.templateproject.notes.featureaddeditnote.presentation.utils.AddEditNoteTextFieldState
import com.jj.templateproject.notes.featureaddeditnote.presentation.utils.AddEditNoteUIEffect.SaveNote
import com.jj.templateproject.notes.featureaddeditnote.presentation.utils.AddEditNoteUIEffect.ShowToast
import com.jj.templateproject.notes.featureaddeditnote.presentation.utils.AddEditNoteViewEvent
import com.jj.templateproject.notes.featureaddeditnote.presentation.viewmodels.AddEditNoteViewModel
import com.jj.templateproject.notes.featurenote.domain.model.Note
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddEditNoteFragment : BaseFragment(R.layout.fragment_add_edit_note) {

    private val addEditNoteViewModel: AddEditNoteViewModel by viewModel()

    private lateinit var fragmentAddEditNoteBinding: FragmentAddEditNoteBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        FragmentAddEditNoteBinding.inflate(inflater, container, false).let { binding ->
            fragmentAddEditNoteBinding = binding
            binding.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
        setupFieldsEventsListeners()
    }

    private fun setupClickListeners() {
        setupColorButtonsClickListeners()
        fragmentAddEditNoteBinding.saveNoteButton.setOnClickListener {
            addEditNoteViewModel.onEvent(AddEditNoteViewEvent.SaveNote)
        }
    }

    private fun setupColorButtonsClickListeners() {
        fragmentAddEditNoteBinding.firstColorButton.setOnClickListener {
            addEditNoteViewModel.onEvent(AddEditNoteViewEvent.ChangeColor(Note.noteColors.first()))
        }
        fragmentAddEditNoteBinding.secondColorButton.setOnClickListener {
            addEditNoteViewModel.onEvent(AddEditNoteViewEvent.ChangeColor(Note.noteColors[1]))
        }
        fragmentAddEditNoteBinding.thirdColorButton.setOnClickListener {
            addEditNoteViewModel.onEvent(AddEditNoteViewEvent.ChangeColor(Note.noteColors[2]))
        }
        fragmentAddEditNoteBinding.fourthColorButton.setOnClickListener {
            addEditNoteViewModel.onEvent(AddEditNoteViewEvent.ChangeColor(Note.noteColors[3]))
        }
        fragmentAddEditNoteBinding.fifthColorButton.setOnClickListener {
            addEditNoteViewModel.onEvent(AddEditNoteViewEvent.ChangeColor(Note.noteColors[4]))
        }
        fragmentAddEditNoteBinding.sixthColorButton.setOnClickListener {
            addEditNoteViewModel.onEvent(AddEditNoteViewEvent.ChangeColor(Note.noteColors.last()))
        }
    }

    private fun setupFieldsEventsListeners() {
        with(fragmentAddEditNoteBinding.noteTitleInput) {
            setOnKeyListener { _, _, _ ->
                addEditNoteViewModel.onEvent(AddEditNoteViewEvent.EnteredTitleCharacter(text.toString()))
                true
            }

            setOnFocusChangeListener { _, hasFocus ->
                addEditNoteViewModel.onEvent(AddEditNoteViewEvent.ChangeTitleFocus(hasFocus))
            }
        }

        with(fragmentAddEditNoteBinding.noteContentInput) {
            setOnKeyListener { _, _, _ ->
                addEditNoteViewModel.onEvent(AddEditNoteViewEvent.EnteredContentCharacter(text.toString()))
                true
            }

            setOnFocusChangeListener { _, hasFocus ->
                addEditNoteViewModel.onEvent(AddEditNoteViewEvent.ChangeContentFocus(hasFocus))
            }
        }
    }

    override fun setupSubscriptions() {
        addEditNoteViewModel.stateLiveData.observe(viewLifecycleOwner) { state ->
            setLoadingPopupVisibility(state.isLoading)
            setupNoteContent(state.noteContentState)
            setupNoteTitle(state.noteTitleState)
            setupBackgroundColor(state.currentlySelectedColor)
        }

        lifecycleScope.launch {
            addEditNoteViewModel.addEditNoteUIEffects.collect { addEditNoteUIEffect ->
                when (addEditNoteUIEffect) {
                    SaveNote -> findNavController().navigateUp()
                    is ShowToast -> Toast.makeText(requireContext(), addEditNoteUIEffect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupBackgroundColor(currentlySelectedColor: Int) {
        fragmentAddEditNoteBinding.addEditNoteLayoutContainer.setBackgroundColor(currentlySelectedColor)
    }

    private fun setupNoteTitle(noteTitleState: AddEditNoteTextFieldState) {
        with(fragmentAddEditNoteBinding.noteTitleInput) {
//            text = noteContentState.content
            hint = if (noteTitleState.isHintVisible) noteTitleState.hint else ""
        }
    }

    private fun setupNoteContent(noteContentState: AddEditNoteTextFieldState) {
        with(fragmentAddEditNoteBinding.noteContentInput) {
//            text = noteContentState.content
            hint = if (noteContentState.isHintVisible) noteContentState.hint else ""
        }
    }
}