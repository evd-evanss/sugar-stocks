package com.sugarspoon.domain.features.local

import com.sugarspoon.domain.base.BaseCoroutineTestWithTestDispatcherProvider
import com.sugarspoon.domain.model.local.SummaryStockEntity
import com.sugarspoon.domain.repositories.LocalRepository
import com.sugarspoon.domain.usecase.local.EditPreferenceUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class EditPreferencesUseCaseTest : BaseCoroutineTestWithTestDispatcherProvider(
    dispatcher = StandardTestDispatcher()
) {

    @MockK
    private lateinit var repository: LocalRepository

    private lateinit var useCase: EditPreferenceUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        useCase = EditPreferenceUseCase(repository)
    }

    @Test
    fun `invoke calls getDetail method of repository`() = runTest {
        val data = SummaryStockEntity(code = "", name = "", sector = "", logo = "")
        val expectedResult = 22
        coEvery { repository.edit(data) } returns expectedResult

        useCase.invoke(data)

        coVerify(exactly = 1) {
            repository.edit(data)
        }
    }
}