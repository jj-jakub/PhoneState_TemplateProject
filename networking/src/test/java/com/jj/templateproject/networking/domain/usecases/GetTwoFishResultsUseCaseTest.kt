package com.jj.templateproject.networking.domain.usecases

import com.jj.templateproject.core.data.text.TextHelper
import com.jj.templateproject.core.domain.result.DataResult
import com.jj.templateproject.core.domain.result.UseCaseResult.Error
import com.jj.templateproject.core.domain.result.UseCaseResult.Success
import com.jj.templateproject.networking.data.repositories.FishDataRepository
import com.jj.templateproject.networking.utils.TestCoroutineScopeProvider
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
class GetTwoFishResultsUseCaseTest {

    @MockK
    private lateinit var textHelper: TextHelper

    @MockK
    private lateinit var fishDataRepository: FishDataRepository

    private lateinit var getTwoFishResultsUseCase: GetTwoFishResultsUseCase

    private lateinit var testCoroutineScopeProvider: TestCoroutineScopeProvider

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        getTwoFishResultsUseCase = GetTwoFishResultsUseCase(fishDataRepository, textHelper)
        testCoroutineScopeProvider = TestCoroutineScopeProvider()
    }

    @Test
    fun `when repository successfully returns data then should return UseCaseSuccess`() =
        testCoroutineScopeProvider.createIOScope().runBlockingTest {
            coEvery { fishDataRepository.fetchSpecifiedSpeciesInfo(any()) } returns DataResult.Success(listOf())

            val result = getTwoFishResultsUseCase()

            assertTrue(result is Success)
        }

    @Test
    fun `when repository successfully returns data then should return UseCaseError`() =
        testCoroutineScopeProvider.createIOScope().runBlockingTest {
            val exception = Exception("Some exception")
            coEvery { fishDataRepository.fetchSpecifiedSpeciesInfo(any()) } returns DataResult.Error(exception)

            val result = getTwoFishResultsUseCase()

            assertTrue(result is Error)
            assertEquals(exception, (result as Error).exception)
        }

    @Test
    fun `when textHelper throws exception then should return UseCaseError`() =
        testCoroutineScopeProvider.createIOScope().runBlockingTest {
            val exception = Exception("HTML conversion error")
            coEvery { fishDataRepository.fetchSpecifiedSpeciesInfo(any()) } returns DataResult.Success(listOf(mockk(relaxed = true)))
            coEvery { textHelper.htmlToString(any()) } throws exception

            val result = getTwoFishResultsUseCase()

            assertTrue(result is Error)
            assertEquals(exception, (result as Error).exception)
        }

    @Test
    fun `when second API call fails then should return Error with data containing info from first API call`() =
        testCoroutineScopeProvider.createIOScope().runBlockingTest {
            val exception = Exception("Some exception")

            coEvery { textHelper.htmlToString(any()) } returns "stringData"
            coEvery { fishDataRepository.fetchSpecifiedSpeciesInfo(any()) } answers {
                DataResult.Success(listOf(mockk(relaxed = true)))
            } andThenAnswer { DataResult.Error(exception) }

            val result = getTwoFishResultsUseCase()

            assertEquals(1, (result as Error).dataValue.size)
            assertEquals("stringData", result.dataValue.first().fishName)
            assertEquals(exception, result.exception)
        }

    @Test
    fun `when text conversion for second API call fails then should return Error with data containing info from first API call`() =
        testCoroutineScopeProvider.createIOScope().runBlockingTest {
            val exception = Exception("HTML conversion error")

            coEvery { fishDataRepository.fetchSpecifiedSpeciesInfo(any()) } answers {
                coEvery { textHelper.htmlToString(any()) } answers { "FirstItemData" }
                DataResult.Success(listOf(mockk(relaxed = true)))
            } andThenAnswer {
                coEvery { textHelper.htmlToString(any()) } throws exception
                DataResult.Success(listOf(mockk(relaxed = true)))
            }

            val result = getTwoFishResultsUseCase()

            assertEquals(1, (result as Error).dataValue.size)
            assertEquals("FirstItemData", result.dataValue.first().fishName)
            assertEquals(exception, result.exception)
        }
}