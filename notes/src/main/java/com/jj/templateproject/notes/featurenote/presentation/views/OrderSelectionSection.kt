package com.jj.templateproject.notes.featurenote.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.jj.templateproject.notes.databinding.OrderSelectionSectionBinding
import com.jj.templateproject.notes.featurenote.domain.utils.NoteOrder
import com.jj.templateproject.notes.featurenote.domain.utils.OrderType

class OrderSelectionSection @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: OrderSelectionSectionBinding = OrderSelectionSectionBinding.inflate(LayoutInflater.from(context), this, true)

    private var noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
    private var onOrderChange: ((NoteOrder) -> Unit)? = null

    init {
        refreshRadioButtonsState()
        setRadioButtonsOnClickListeners()
    }

    fun setOnOrderChangeListener(onOrderChange: (NoteOrder) -> Unit) {
        this.onOrderChange = onOrderChange
    }

    fun setNoteOrder(noteOrder: NoteOrder) {
        this.noteOrder = noteOrder
        refreshRadioButtonsState()
    }

    private fun refreshRadioButtonsState() {
        with(binding) {
            dateOrderRadioButton.isChecked = noteOrder is NoteOrder.Date
            titleOrderRadioButton.isChecked = noteOrder is NoteOrder.Title
            colorOrderRadioButton.isChecked = noteOrder is NoteOrder.Color
            ascendingOrderRadioButton.isChecked = noteOrder.orderType is OrderType.Ascending
            descendingOrderRadioButton.isChecked = noteOrder.orderType is OrderType.Descending
        }
    }

    private fun setRadioButtonsOnClickListeners() {
        with(binding) {
            dateOrderRadioButton.setOnClickListener {
                onOrderChange?.invoke(NoteOrder.Date(noteOrder.orderType))
            }
            titleOrderRadioButton.setOnClickListener {
                onOrderChange?.invoke(NoteOrder.Title(noteOrder.orderType))
            }
            colorOrderRadioButton.setOnClickListener {
                onOrderChange?.invoke(NoteOrder.Color(noteOrder.orderType))
            }
            ascendingOrderRadioButton.setOnClickListener {
                onOrderChange?.invoke(noteOrder.copy(OrderType.Ascending))
            }
            descendingOrderRadioButton.setOnClickListener {
                onOrderChange?.invoke(noteOrder.copy(OrderType.Descending))
            }
        }
    }

    fun getAscBtn() = binding.ascendingOrderRadioButton
    fun getColorBtn() = binding.colorOrderRadioButton
}