package com.jj.templateproject.framework.presentation.viewholders

import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.jj.templateproject.databinding.StatesRowFourthLayoutBinding
import com.jj.templateproject.framework.presentation.adapters.statelistitems.StateListItemData
import com.jj.templateproject.framework.presentation.adapters.statelistitems.color

class StatesRowFourthViewHolder(private val binding: StatesRowFourthLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: StateListItemData) {
        with(binding) {
            fourthStateIcon.setBackgroundColor(getBackgroundColor(item.isKnown, item.isActive))
            fourthStateIcon2.setBackgroundColor(getBackgroundColor(item.isKnown, item.isActive))
            fourthStateIcon3.setBackgroundColor(getBackgroundColor(item.isKnown, item.isActive))
            fourthStateIcon4.setBackgroundColor(getBackgroundColor(item.isKnown, item.isActive))
            fourthStateLabel.text = item.name
            fourthStateValue.text = item.additionalInfo
            fourthAdditionalValue.setTextColor(ContextCompat.getColor(binding.root.context, item.type.color))
        }
    }

    private fun getBackgroundColor(isKnown: Boolean, active: Boolean): Int {
        return if (!isKnown) Color.GRAY
        else if (active) Color.GREEN else Color.RED
    }
}