package com.jj.templateproject.framework.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.jj.templateproject.databinding.StatesRowNetworkLayoutBinding
import com.jj.templateproject.framework.presentation.viewholders.StatesRowNetworkViewHolder

object StatesViewTypes {
    const val TYPE_1 = 135
    const val TYPE_2 = 241135512
}

interface StateListItemData {
    val isKnown: Boolean
    val isActive: Boolean
    val name: String
    val additionalInfo: String
}

class StateListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items: ArrayList<StateListItemData> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            StatesViewTypes.TYPE_1 -> StatesRowNetworkViewHolder(parent.createBinding(StatesRowNetworkLayoutBinding::inflate))
            StatesViewTypes.TYPE_2 -> StatesRowNetworkViewHolder(parent.createBinding(StatesRowNetworkLayoutBinding::inflate))
            else -> StatesRowNetworkViewHolder(parent.createBinding(StatesRowNetworkLayoutBinding::inflate))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]

        when (holder) {
            is StatesRowNetworkViewHolder -> holder.bind(item)
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