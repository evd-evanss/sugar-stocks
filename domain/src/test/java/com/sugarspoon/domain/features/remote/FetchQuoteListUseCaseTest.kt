package com.sugarspoon.domain.features.remote

import com.sugarspoon.domain.base.BaseCoroutineTestWithTestDispatcherProvider
import com.sugarspoon.domain.model.remote.BrapiResponseDto
import com.sugarspoon.domain.repositories.ApiRepository
import com.sugarspoon.domain.usecase.remote.FetchQuoteListUseCase
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

class FetchQuoteListUseCaseTest : BaseCoroutineTestWithTestDispatcherProvider(
    dispatcher = StandardTestDispatcher()
) {

    @MockK
    private lateinit var repository: ApiRepository

    private lateinit var useCase: FetchQuoteListUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        useCase = FetchQuoteListUseCase(repository)
    }

    @Test
    fun `invoke calls getQuoteList method of repository`() = runTest {
        val expectedResult = flowOf(BrapiResponseDto(listOf(), listOf()))
        coEvery { repository.getQuoteList() } returns expectedResult

        val actualResult = useCase.invoke()

        coVerify(exactly = 1) {
            repository.getQuoteList()
        }

        expectThat(actualResult).isEqualTo(expectedResult)
    }
}