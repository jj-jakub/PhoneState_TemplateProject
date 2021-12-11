package com.jj.templateproject.core.framework.presentation.adapters.statelistitems

data class StatesRowNetworkItemData(
    override val isKnown: Boolean, override val isActive: Boolean, override val name: String, override val additionalInfo: String
) : StateListItemData {

    override val type: StatesViewTypes
        get() = StatesViewTypes.Type1
}