package com.jj.templateproject.framework.presentation.adapters.statelistitems

interface StateListItemData {
    val isKnown: Boolean
    val isActive: Boolean
    val name: String
    val additionalInfo: String
    val type: Int
}