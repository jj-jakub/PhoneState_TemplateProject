package com.jj.templateproject.networking.framework.presentation.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jj.templateproject.core.framework.presentation.createBinding
import com.jj.templateproject.networking.databinding.FishDetailsDefaultViewHolderBinding
import com.jj.templateproject.networking.framework.presentation.adapters.fishlistitems.FishItemViewData
import com.jj.templateproject.networking.framework.presentation.viewholders.FishDetailsDefaultViewHolder

class FishResultsListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items: ArrayList<FishItemViewData> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FishDetailsDefaultViewHolder(parent.createBinding(FishDetailsDefaultViewHolderBinding::inflate))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        (holder as FishDetailsDefaultViewHolder).bind(item)
    }

    override fun getItemCount() = items.size

    fun setItems(newItems: List<FishItemViewData>) {
        items.clear()
        items.addAll(newItems)
    }
}