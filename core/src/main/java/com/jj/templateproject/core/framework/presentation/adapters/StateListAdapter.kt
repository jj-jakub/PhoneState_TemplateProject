package com.jj.templateproject.core.framework.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.jj.templateproject.core.databinding.StatesRowDefaultLayoutBinding
import com.jj.templateproject.core.databinding.StatesRowFourthLayoutBinding
import com.jj.templateproject.core.databinding.StatesRowNetworkLayoutBinding
import com.jj.templateproject.core.databinding.StatesRowSecondLayoutBinding
import com.jj.templateproject.core.databinding.StatesRowThirdLayoutBinding
import com.jj.templateproject.core.framework.presentation.adapters.statelistitems.StateListItemData
import com.jj.templateproject.core.framework.presentation.adapters.statelistitems.StatesViewTypes
import com.jj.templateproject.core.framework.presentation.viewholders.StatesRowDefaultViewHolder
import com.jj.templateproject.core.framework.presentation.viewholders.StatesRowFourthViewHolder
import com.jj.templateproject.core.framework.presentation.viewholders.StatesRowNetworkViewHolder
import com.jj.templateproject.core.framework.presentation.viewholders.StatesRowSecondViewHolder
import com.jj.templateproject.core.framework.presentation.viewholders.StatesRowThirdViewHolder

class StateListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items: ArrayList<StateListItemData> = arrayListOf()

    override fun getItemViewType(position: Int): Int = items[position].type.id

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            StatesViewTypes.Type1.id -> StatesRowNetworkViewHolder(parent.createBinding(StatesRowNetworkLayoutBinding::inflate))
            StatesViewTypes.Type2.id -> StatesRowSecondViewHolder(parent.createBinding(StatesRowSecondLayoutBinding::inflate))
            StatesViewTypes.Type3.id -> StatesRowThirdViewHolder(parent.createBinding(StatesRowThirdLayoutBinding::inflate))
            StatesViewTypes.Type4.id -> StatesRowFourthViewHolder(parent.createBinding(StatesRowFourthLayoutBinding::inflate))
            else -> StatesRowDefaultViewHolder(parent.createBinding(StatesRowDefaultLayoutBinding::inflate))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        when (holder) {
            is StatesRowNetworkViewHolder -> holder.bind(item)
            is StatesRowSecondViewHolder -> holder.bind(item)
            is StatesRowThirdViewHolder -> holder.bind(item)
            is StatesRowFourthViewHolder -> holder.bind(item)
            is StatesRowDefaultViewHolder -> holder.bind(item)
        }
    }

    override fun getItemCount(): Int = items.size

    fun setItems(newItems: List<StateListItemData>) {
        items.clear()
        items.addAll(newItems)
    }

    private inline fun <T : ViewBinding> ViewGroup.createBinding(
        bindingFactory: (inflater: LayoutInflater, parent: ViewGroup, attach: Boolean) -> T
    ) =
        bindingFactory(LayoutInflater.from(context), this, false)
}