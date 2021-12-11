package com.jj.templateproject.core.framework.presentation.adapters.statelistitems

class StatesRowFourthViewItemData(
    override val isKnown: Boolean, override val isActive: Boolean, override val name: String, override val additionalInfo: String
) : StateListItemData {

    override val type: StatesViewTypes
        get() = StatesViewTypes.Type4
}