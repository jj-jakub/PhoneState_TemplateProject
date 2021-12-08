package com.jj.templateproject.framework.presentation.adapters.statelistitems

data class StatesRowSecondViewItemData(
    override val isKnown: Boolean, override val isActive: Boolean, override val name: String, override val additionalInfo: String
) : StateListItemData {

    override val type: Int
        get() = StatesViewTypes.TYPE_2
}