package com.jj.templateproject.networking.domain.usecases

import com.jj.templateproject.core.data.text.TextHelper
import com.jj.templateproject.core.domain.result.DataResult
import com.jj.templateproject.core.domain.result.UseCaseResult
import com.jj.templateproject.networking.utils.TestCoroutineScopeProvider
import com.jj.templateproject.networking.data.repositories.FishDataRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class GetAllFishResultsUseCaseTest {

    @MockK
    private lateinit var textHelper: TextHelper

    @MockK
    private lateinit var fishDataRepository: FishDataRepository

    private lateinit var getAllFishResultsUseCase: GetAllFishResultsUseCase

    private lateinit var testCoroutineScopeProvider: TestCoroutineScopeProvider

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        getAllFishResultsUseCase = GetAllFishResultsUseCase(fishDataRepository, textHelper)
        testCoroutineScopeProvider = TestCoroutineScopeProvider()
    }

    @Test
    fun `when repository successfully returns data then should return UseCaseSuccess`() =
        testCoroutineScopeProvider.createIOScope().runBlockingTest {
            coEvery { fishDataRepository.fetchAllSpecies() } returns DataResult.Success(listOf())

            val result = getAllFishResultsUseCase()

            assertTrue(result is UseCaseResult.Success)
        }

    @Test
    fun `when repository successfully returns data then should return UseCaseError`() =
        testCoroutineScopeProvider.createIOScope().runBlockingTest {
            val exception = Exception("Some exception")
            coEvery { fishDataRepository.fetchAllSpecies() } returns DataResult.Error(exception)

            val result = getAllFishResultsUseCase()

            assertTrue(result is UseCaseResult.Error)
            assertEquals(exception, (result as UseCaseResult.Error).exception)
        }

    @Test
    fun `when textHelper throws exception then should return UseCaseError`() =
        testCoroutineScopeProvider.createIOScope().runBlockingTest {
            val exception = Exception("HTML conversion error")
            coEvery { fishDataRepository.fetchAllSpecies() } returns DataResult.Success(listOf(mockk(relaxed = true)))
            coEvery { textHelper.htmlToString(any()) } throws exception

            val result = getAllFishResultsUseCase()

            assertTrue(result is UseCaseResult.Error)
            assertEquals(exception, (result as UseCaseResult.Error).exception)
        }
}