package com.jj.templateproject.framework.presentation.viewholders

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.jj.templateproject.databinding.StatesRowThirdLayoutBinding
import com.jj.templateproject.framework.presentation.adapters.statelistitems.StateListItemData

class StatesRowThirdViewHolder(private val binding: StatesRowThirdLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: StateListItemData) {
        with(binding) {
            thirdStateIcon.setBackgroundColor(getBackgroundColor(item.isKnown, item.isActive))
            thirdStateIcon2.setBackgroundColor(getBackgroundColor(item.isKnown, item.isActive))
            thirdStateIcon3.setBackgroundColor(getBackgroundColor(item.isKnown, item.isActive))
            thirdStateLabel.text = item.name
            thirdStateValue.text = item.additionalInfo
        }
    }

    private fun getBackgroundColor(isKnown: Boolean, active: Boolean): Int {
        return if (!isKnown) Color.GRAY
        else if (active) Color.GREEN else Color.RED
    }
}