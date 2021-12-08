package com.jj.templateproject.framework.presentation.adapters.statelistitems

class StatesRowFourthViewItemData(
    override val isKnown: Boolean, override val isActive: Boolean, override val name: String, override val additionalInfo: String
) : StateListItemData {

    override val type: Int
        get() = StatesViewTypes.TYPE_4
}