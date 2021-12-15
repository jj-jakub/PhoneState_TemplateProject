package com.jj.templateproject.networking.framework.presentation.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.jj.templateproject.networking.databinding.FishDetailsDefaultViewHolderBinding
import com.jj.templateproject.networking.framework.presentation.adapters.fishlistitems.FishItemViewData

class FishDetailsDefaultViewHolder(private val binding: FishDetailsDefaultViewHolderBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: FishItemViewData) {
        with(binding) {
            fishNameValue.text = item.fishName
            fishBiologyValue.text = item.fishBiology
            fishRegionValue.text = item.fishRegion
            fishSugarsTotalValue.text = item.fishSugars
            fishTasteValue.text = item.fishTaste
        }
    }
}