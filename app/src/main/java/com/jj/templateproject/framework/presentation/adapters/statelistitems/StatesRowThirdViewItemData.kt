package com.jj.templateproject.framework.presentation.adapters.statelistitems

class StatesRowThirdViewItemData(
    override val isKnown: Boolean, override val isActive: Boolean, override val name: String, override val additionalInfo: String
) : StateListItemData {

    override val type: StatesViewTypes
        get() = StatesViewTypes.Type3
}