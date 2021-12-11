package com.jj.templateproject.core.framework.presentation.viewholders

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.jj.templateproject.core.databinding.StatesRowDefaultLayoutBinding
import com.jj.templateproject.core.framework.presentation.adapters.statelistitems.StateListItemData

class StatesRowDefaultViewHolder(private val binding: StatesRowDefaultLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: StateListItemData) {
        with(binding) {
            defaultStateIcon.setBackgroundColor(getBackgroundColor(item.isKnown, item.isActive))
            defaultValueLabel.text = item.name
        }
    }

    private fun getBackgroundColor(isKnown: Boolean, active: Boolean): Int {
        return if (!isKnown) Color.GRAY
        else if (active) Color.GREEN else Color.RED
    }
}