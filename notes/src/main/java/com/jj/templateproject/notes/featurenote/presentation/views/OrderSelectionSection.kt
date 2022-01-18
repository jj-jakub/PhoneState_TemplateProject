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

    private fun refreshRadioButtonsState() {
        with(binding) {
            dateOrderRadioButton.isSelected = noteOrder is NoteOrder.Date
            titleOrderRadioButton.isSelected = noteOrder is NoteOrder.Title
            colorOrderRadioButton.isSelected = noteOrder is NoteOrder.Color
            ascendingOrderRadioButton.isSelected = noteOrder.orderType is OrderType.Ascending
            descendingOrderRadioButton.isSelected = noteOrder.orderType is OrderType.Descending
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
}