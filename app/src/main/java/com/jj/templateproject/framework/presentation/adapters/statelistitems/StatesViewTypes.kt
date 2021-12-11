package com.jj.templateproject.framework.presentation.adapters.statelistitems

import com.jj.templateproject.R
import com.jj.templateproject.framework.presentation.adapters.statelistitems.StatesViewTypes.Type1
import com.jj.templateproject.framework.presentation.adapters.statelistitems.StatesViewTypes.Type2
import com.jj.templateproject.framework.presentation.adapters.statelistitems.StatesViewTypes.Type3
import com.jj.templateproject.framework.presentation.adapters.statelistitems.StatesViewTypes.Type4

sealed class StatesViewTypes(val id: Int) {
    object Type1 : StatesViewTypes(135)
    object Type2 : StatesViewTypes(241135512)
    object Type3 : StatesViewTypes(-12)
    object Type4 : StatesViewTypes(-32124)
}

val StatesViewTypes.color: Int
    get() {
        return when (this) {
            is Type1 -> R.color.red
            is Type2 -> R.color.yellow
            is Type3 -> R.color.blue
            is Type4 -> R.color.green
        }
    }