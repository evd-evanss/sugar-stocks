package com.sugarspoon.domain.features.local

import com.sugarspoon.domain.base.BaseCoroutineTestWithTestDispatcherProvider
import com.sugarspoon.domain.repositories.LocalRepository
import com.sugarspoon.domain.usecase.local.DeletePreferenceUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DeletePreferencesUseCaseTest : BaseCoroutineTestWithTestDispatcherProvider(
    dispatcher = StandardTestDispatcher()
) {

    @MockK
    private lateinit var repository: LocalRepository

    private lateinit var useCase: DeletePreferenceUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        useCase = DeletePreferenceUseCase(repository)
    }

    @Test
    fun `invoke calls getDetail method of repository`() = runTest {
        val code = "PETR4"
        val expectedResult = 1
        coEvery { repository.delete(code) } returns expectedResult

        useCase.invoke(code)

        coVerify(exactly = 1) {
            repository.delete(code)
        }
    }
}