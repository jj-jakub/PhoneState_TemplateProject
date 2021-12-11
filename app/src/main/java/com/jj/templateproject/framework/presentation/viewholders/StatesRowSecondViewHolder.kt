package com.jj.templateproject.framework.presentation.viewholders

import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.jj.templateproject.databinding.StatesRowSecondLayoutBinding
import com.jj.templateproject.framework.presentation.adapters.statelistitems.StateListItemData
import com.jj.templateproject.framework.presentation.adapters.statelistitems.color

class StatesRowSecondViewHolder(private val binding: StatesRowSecondLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: StateListItemData) {
        with(binding) {
            secondStateIcon.setBackgroundColor(getBackgroundColor(item.isKnown, item.isActive))
            secondStateIcon2.setBackgroundColor(getBackgroundColor(item.isKnown, item.isActive))
            secondStateLabel.text = item.name
            secondStateValue.text = item.additionalInfo
            secondAdditionalValue.setTextColor(ContextCompat.getColor(binding.root.context, item.type.color))
        }
    }

    private fun getBackgroundColor(isKnown: Boolean, active: Boolean): Int {
        return if (!isKnown) Color.GRAY
        else if (active) Color.GREEN else Color.RED
    }
}