package com.jj.templateproject.framework.presentation.adapters.statelistitems

import com.jj.templateproject.R
import com.jj.templateproject.core.framework.presentation.adapters.statelistitems.StatesViewTypes
import com.jj.templateproject.core.framework.presentation.adapters.statelistitems.color
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class StatesViewTypesTest {

    @Test
    fun `should return red color resource for Type1`() {
        val statesViewType = StatesViewTypes.Type1

        val colorRes = statesViewType.color

        assertEquals(R.color.red, colorRes)
    }

    @Test
    fun `should return yellow color resource for Type2`() {
        val statesViewType = StatesViewTypes.Type2

        val colorRes = statesViewType.color

        assertEquals(R.color.yellow, colorRes)
    }

    @Test
    fun `should return blue color resource for Type3`() {
        val statesViewType = StatesViewTypes.Type3

        val colorRes = statesViewType.color

        assertEquals(R.color.blue, colorRes)
    }

    @Test
    fun `should return green color resource for Type4`() {
        val statesViewType = StatesViewTypes.Type4

        val colorRes = statesViewType.color

        assertEquals(R.color.green, colorRes)
    }
}