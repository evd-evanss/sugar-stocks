package com.sugarspoon.domain.features.remote

import com.sugarspoon.domain.base.BaseCoroutineTestWithTestDispatcherProvider
import com.sugarspoon.domain.model.remote.QuotesResponseDto
import com.sugarspoon.domain.repositories.ApiRepository
import com.sugarspoon.domain.usecase.remote.FetchDetailUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class FetchDetailUseCaseTest : BaseCoroutineTestWithTestDispatcherProvider(
    dispatcher = StandardTestDispatcher()
) {

    @MockK
    private lateinit var repository: ApiRepository

    private lateinit var useCase: FetchDetailUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        useCase = FetchDetailUseCase(repository)
    }

    @Test
    fun `invoke calls getDetail method of repository`() = runTest {
        val expectedResult = Result.success(QuotesResponseDto("", listOf()))
        coEvery { repository.getDetail(tickers = "PETR4") } returns expectedResult

        val actualResult = useCase.invoke("PETR4")

        coVerify(exactly = 1) {
            repository.getDetail("PETR4")
        }

        expectThat(actualResult).isEqualTo(expectedResult)
    }
}