package com.jj.templateproject.notes.featureaddeditnote.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.jj.templateproject.core.framework.presentation.fragments.BaseFragment
import com.jj.templateproject.notes.R
import com.jj.templateproject.notes.databinding.FragmentAddEditNoteBinding
import com.jj.templateproject.notes.featureaddeditnote.presentation.utils.AddEditNoteTextFieldState
import com.jj.templateproject.notes.featureaddeditnote.presentation.utils.AddEditNoteUIEffect.SaveNote
import com.jj.templateproject.notes.featureaddeditnote.presentation.utils.AddEditNoteUIEffect.ShowToast
import com.jj.templateproject.notes.featureaddeditnote.presentation.utils.AddEditNoteViewEvent
import com.jj.templateproject.notes.featureaddeditnote.presentation.utils.OnTextChangedWatcher
import com.jj.templateproject.notes.featureaddeditnote.presentation.viewmodels.AddEditNoteViewModel
import com.jj.templateproject.notes.featurenote.domain.model.Note
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

const val NOTE_TO_EDIT_ID = "NOTE_TO_EDIT_ID"

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

        val noteToEditId: Int? = arguments?.getInt(NOTE_TO_EDIT_ID)
        if (noteToEditId != null) {
            loadNote(noteToEditId)
        }
        setupColorButtons()
        setupFieldsEventsListeners()
        fragmentAddEditNoteBinding.saveNoteButton.setOnClickListener {
            addEditNoteViewModel.onEvent(AddEditNoteViewEvent.SaveNote)
        }
    }

    private fun loadNote(noteToEditId: Int) {
        addEditNoteViewModel.onEvent(AddEditNoteViewEvent.LoadNote(noteToEditId))
    }

    private fun setupColorButtons() {
        with(fragmentAddEditNoteBinding.firstColorButton) {
            val buttonColor = Note.noteColorsResources.first()
            backgroundTintList = AppCompatResources.getColorStateList(requireContext(), buttonColor)
            setOnClickListener { addEditNoteViewModel.onEvent(AddEditNoteViewEvent.ChangeColor(buttonColor)) }
        }
        with(fragmentAddEditNoteBinding.secondColorButton) {
            val buttonColor = Note.noteColorsResources[1]
            backgroundTintList = AppCompatResources.getColorStateList(requireContext(), buttonColor)
            setOnClickListener { addEditNoteViewModel.onEvent(AddEditNoteViewEvent.ChangeColor(buttonColor)) }
        }
        with(fragmentAddEditNoteBinding.thirdColorButton) {
            val buttonColor = Note.noteColorsResources[2]
            backgroundTintList = AppCompatResources.getColorStateList(requireContext(), buttonColor)
            setOnClickListener { addEditNoteViewModel.onEvent(AddEditNoteViewEvent.ChangeColor(buttonColor)) }
        }
        with(fragmentAddEditNoteBinding.fourthColorButton) {
            val buttonColor = Note.noteColorsResources[3]
            backgroundTintList = AppCompatResources.getColorStateList(requireContext(), buttonColor)
            setOnClickListener { addEditNoteViewModel.onEvent(AddEditNoteViewEvent.ChangeColor(buttonColor)) }
        }
        with(fragmentAddEditNoteBinding.fifthColorButton) {
            val buttonColor = Note.noteColorsResources[4]
            backgroundTintList = AppCompatResources.getColorStateList(requireContext(), buttonColor)
            setOnClickListener { addEditNoteViewModel.onEvent(AddEditNoteViewEvent.ChangeColor(buttonColor)) }
        }
        with(fragmentAddEditNoteBinding.sixthColorButton) {
            val buttonColor = Note.noteColorsResources.last()
            backgroundTintList = AppCompatResources.getColorStateList(requireContext(), buttonColor)
            setOnClickListener { addEditNoteViewModel.onEvent(AddEditNoteViewEvent.ChangeColor(Note.noteColorsResources.last())) }
        }
    }

    private fun setupFieldsEventsListeners() {
        with(fragmentAddEditNoteBinding.noteTitleInput) {
            addTextChangedListener(OnTextChangedWatcher {
                addEditNoteViewModel.onEvent(AddEditNoteViewEvent.EnteredTitleCharacter(text.toString()))
            })
            setOnFocusChangeListener { _, hasNoFocus ->
                addEditNoteViewModel.onEvent(AddEditNoteViewEvent.ChangeTitleFocus(hasNoFocus.not()))
            }
        }

        with(fragmentAddEditNoteBinding.noteContentInput) {
            addTextChangedListener(OnTextChangedWatcher {
                addEditNoteViewModel.onEvent(AddEditNoteViewEvent.EnteredContentCharacter(text.toString()))
            })
            setOnFocusChangeListener { _, hasNoFocus ->
                addEditNoteViewModel.onEvent(AddEditNoteViewEvent.ChangeContentFocus(hasNoFocus.not()))
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

    private fun setupBackgroundColor(@ColorRes currentlySelectedColor: Int) {
        fragmentAddEditNoteBinding.addEditNoteLayoutContainer.setBackgroundResource(currentlySelectedColor)
    }

    private fun setupNoteTitle(noteTitleState: AddEditNoteTextFieldState) {
        with(fragmentAddEditNoteBinding.noteTitleInput) {
            if (!hasFocus()) setText(noteTitleState.content)
            hint = if (noteTitleState.isHintVisible) noteTitleState.hint else ""
        }
    }

    private fun setupNoteContent(noteContentState: AddEditNoteTextFieldState) {
        with(fragmentAddEditNoteBinding.noteContentInput) {
            if (!hasFocus()) setText(noteContentState.content)
            hint = if (noteContentState.isHintVisible) noteContentState.hint else ""
        }
    }
}