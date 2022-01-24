package com.jj.templateproject.networking.domain.usecases

import com.jj.templateproject.core.domain.result.UseCaseResult
import com.jj.templateproject.networking.framework.presentation.adapters.fishlistitems.DefaultFishItemViewData
import com.jj.templateproject.networking.framework.presentation.adapters.fishlistitems.FishItemViewData
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FilterSpeciesByNameUseCaseTest {

    private lateinit var filterSpeciesByNameUseCase: FilterSpeciesByNameUseCase

    private val firstFishItem = DefaultFishItemViewData("redfish", null, "none", "none", "none")
    private val secondFishItem = DefaultFishItemViewData("none", "redfish", null, "none", "none")
    private val thirdFishItem = DefaultFishItemViewData("none", "none", "redfish", null, "none")
    private val fourthFishItem = DefaultFishItemViewData("none", "none", "none", "redfish", null)
    private val fifthFishItem = DefaultFishItemViewData(null, "none", "none", "none", "redfish")

    @BeforeEach
    fun setup() {
        filterSpeciesByNameUseCase = FilterSpeciesByNameUseCase()
    }

    @Test
    fun `should return UseCaseResult Success as result of filtering`() {
        val fishItemsList: List<FishItemViewData> = listOf()
        val fishName = "fishName"

        val result = filterSpeciesByNameUseCase.invoke(fishName, fishItemsList)

        assertTrue(result is UseCaseResult.Success)
    }

    @Test
    fun `should return list filtered by fishName`() {
        val result = filterSpeciesByNameUseCase.invoke("redfish", getFishItemsList())

        assertEquals(1, result.getValue()?.size)
        assertEquals(firstFishItem, result.getValue()?.first())
    }

    @Test
    fun `should return full original list when fishName is empty`() {
        val result = filterSpeciesByNameUseCase.invoke("", getFishItemsList())

        assertEquals(5, result.getValue()?.size)
        assertEquals(firstFishItem, result.getValue()?.first())
        assertEquals(secondFishItem, result.getValue()?.get(1))
        assertEquals(thirdFishItem, result.getValue()?.get(2))
        assertEquals(fourthFishItem, result.getValue()?.get(3))
        assertEquals(fifthFishItem, result.getValue()?.last())
    }

    @Test
    fun `should return list filtered by partial fishName`() {
        val result = filterSpeciesByNameUseCase.invoke("redf", getFishItemsList())

        result.getValue()?.let { assertEquals(1, it.size) }
        assertEquals(firstFishItem, result.getValue()?.first())
    }

    @Test
    fun `should return list filtered by fishName ignoring case`() {
        val result = filterSpeciesByNameUseCase.invoke("rEDFIsh", getFishItemsList())

        assertEquals(1, result.getValue()?.size)
        assertEquals(firstFishItem, result.getValue()?.first())
    }

    private fun getFishItemsList(): List<DefaultFishItemViewData> =
        listOf(firstFishItem, secondFishItem, thirdFishItem, fourthFishItem, fifthFishItem)
}