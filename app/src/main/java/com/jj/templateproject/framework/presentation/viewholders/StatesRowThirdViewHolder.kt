package com.jj.templateproject.framework.presentation.viewholders

import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.jj.templateproject.databinding.StatesRowThirdLayoutBinding
import com.jj.templateproject.framework.presentation.adapters.statelistitems.StateListItemData
import com.jj.templateproject.framework.presentation.adapters.statelistitems.color

class StatesRowThirdViewHolder(private val binding: StatesRowThirdLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: StateListItemData) {
        with(binding) {
            thirdStateIcon.setBackgroundColor(getBackgroundColor(item.isKnown, item.isActive))
            thirdStateIcon2.setBackgroundColor(getBackgroundColor(item.isKnown, item.isActive))
            thirdStateIcon3.setBackgroundColor(getBackgroundColor(item.isKnown, item.isActive))
            thirdStateLabel.text = item.name
            thirdStateValue.text = item.additionalInfo
            thirdAdditionalValue.setTextColor(ContextCompat.getColor(binding.root.context, item.type.color))
        }
    }

    private fun getBackgroundColor(isKnown: Boolean, active: Boolean): Int {
        return if (!isKnown) Color.GRAY
        else if (active) Color.GREEN else Color.RED
    }
}