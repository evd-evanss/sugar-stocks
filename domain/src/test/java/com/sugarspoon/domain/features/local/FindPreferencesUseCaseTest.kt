package com.sugarspoon.domain.features.local

import com.sugarspoon.domain.base.BaseCoroutineTestWithTestDispatcherProvider
import com.sugarspoon.domain.model.local.SummaryStockEntity
import com.sugarspoon.domain.repositories.LocalRepository
import com.sugarspoon.domain.usecase.local.FindPreferenceUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class FindPreferencesUseCaseTest : BaseCoroutineTestWithTestDispatcherProvider(
    dispatcher = StandardTestDispatcher()
) {

    @MockK
    private lateinit var repository: LocalRepository

    private lateinit var useCase: FindPreferenceUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        useCase = FindPreferenceUseCase(repository)
    }

    @Test
    fun `invoke calls getDetail method of repository`() = runTest {
        val code = "PETR4"
        val expectedResult = flowOf(SummaryStockEntity(code = "", name = "", sector = "", logo = ""))
        coEvery { repository.findNote(code) } returns expectedResult

        useCase.invoke(code)

        coVerify(exactly = 1) {
            repository.findNote(code)
        }
    }
}