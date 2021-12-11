package com.jj.templateproject.core.framework.presentation.adapters.statelistitems

interface StateListItemData {
    val isKnown: Boolean
    val isActive: Boolean
    val name: String
    val additionalInfo: String
    val type: StatesViewTypes
}