package com.jj.templateproject.framework.presentation.viewholders

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.jj.templateproject.databinding.StatesRowNetworkLayoutBinding
import com.jj.templateproject.framework.presentation.adapters.StateListItemData

class StatesRowNetworkViewHolder(private val binding: StatesRowNetworkLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: StateListItemData) {
        with(binding) {
            networkStateIcon.setBackgroundColor(getBackgroundColor(item.isKnown, item.isActive))
            networkStateLabel.text = item.name
            networkStateValue.text = item.additionalInfo
        }
    }

    private fun getBackgroundColor(isKnown: Boolean, active: Boolean): Int {
        return if (!isKnown) Color.GRAY
        else if (active) Color.GREEN else Color.RED
    }
}