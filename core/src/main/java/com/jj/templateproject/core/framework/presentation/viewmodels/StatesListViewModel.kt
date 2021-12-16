package com.jj.templateproject.core.framework.presentation.viewmodels

import com.jj.templateproject.core.framework.presentation.adapters.statelistitems.StateListItemData
import com.jj.templateproject.core.framework.presentation.adapters.statelistitems.StatesRowFourthViewItemData
import com.jj.templateproject.core.framework.presentation.adapters.statelistitems.StatesRowNetworkItemData
import com.jj.templateproject.core.framework.presentation.adapters.statelistitems.StatesRowSecondViewItemData
import com.jj.templateproject.core.framework.presentation.adapters.statelistitems.StatesRowThirdViewItemData
import com.jj.templateproject.core.framework.presentation.viewmodels.StatesListViewModel.ViewAction
import com.jj.templateproject.core.framework.presentation.viewmodels.StatesListViewModel.ViewState
import com.jj.templateproject.core.framework.presentation.viewmodels.states.BaseViewState

class StatesListViewModel : BaseViewModel<ViewState, ViewAction>(ViewState()) {

    data class ViewState(val isLoading: Boolean = false) : BaseViewState

    sealed class ViewAction : BaseViewAction

    private val itemsProvider = ItemsProvider()

    fun getListItems() = itemsProvider.getItems()
}

class ItemsProvider {

    fun getItems(): List<StateListItemData> {
        val firstItem = StatesRowNetworkItemData(isKnown = true, isActive = true, name = "Hey first", additionalInfo = "ad1")
        val secondItem = StatesRowSecondViewItemData(isKnown = true, isActive = false, name = "Hey secondItem", additionalInfo = "ad2")
        val thirdItem = StatesRowThirdViewItemData(isKnown = false, isActive = true, name = "Hey thirdItem", additionalInfo = "ad3")
        val fourthItem = StatesRowFourthViewItemData(isKnown = true, isActive = true, name = "Hey fourthItem", additionalInfo = "ad4")

        return listOf(firstItem, secondItem, thirdItem, fourthItem)
    }
}