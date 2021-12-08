package com.jj.templateproject.framework.viewmodels

import com.jj.templateproject.framework.presentation.adapters.statelistitems.StateListItemData
import com.jj.templateproject.framework.presentation.adapters.statelistitems.StatesRowNetworkItemData

class StatesListViewModel: BaseViewModel() {

    private val itemsProvider = ItemsProvider()

    fun getListItems() = itemsProvider.getItems()
}

class ItemsProvider {

    fun getItems(): List<StateListItemData> {
        val firstItem = StatesRowNetworkItemData(isKnown = true, isActive = true, name = "Hey first", additionalInfo = "ad1")
        val secondItem = StatesRowNetworkItemData(isKnown = true, isActive = false, name = "Hey secondItem", additionalInfo = "ad2")
        val thirdItem = StatesRowNetworkItemData(isKnown = false, isActive = true, name = "Hey thirdItem", additionalInfo = "ad3")
        val fourthItem = StatesRowNetworkItemData(isKnown = true, isActive = true, name = "Hey fourthItem", additionalInfo = "ad4")

        return listOf(firstItem, secondItem, thirdItem, fourthItem)
    }
}