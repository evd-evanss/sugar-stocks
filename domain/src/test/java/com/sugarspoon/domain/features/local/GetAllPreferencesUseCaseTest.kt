package com.sugarspoon.domain.features.local

import com.sugarspoon.domain.base.BaseCoroutineTestWithTestDispatcherProvider
import com.sugarspoon.domain.model.local.SummaryStockEntity
import com.sugarspoon.domain.repositories.LocalRepository
import com.sugarspoon.domain.usecase.local.GetAllPreferenceUseCase
import com.sugarspoon.domain.usecase.local.SavePreferenceUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class GetAllPreferencesUseCaseTest : BaseCoroutineTestWithTestDispatcherProvider(
    dispatcher = StandardTestDispatcher()
) {

    @MockK
    private lateinit var repository: LocalRepository

    private lateinit var useCase: GetAllPreferenceUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        useCase = GetAllPreferenceUseCase(repository)
    }

    @Test
    fun `invoke calls getDetail method of repository`() = runTest {
        val expectedResult = flowOf(listOf<SummaryStockEntity>())
        coEvery { repository.getAllStocks() } returns expectedResult

        val actualResult = useCase.invoke()

        coVerify(exactly = 1) {
            repository.getAllStocks()
        }

        expectThat(actualResult).isEqualTo(expectedResult)
    }
}