package com.sugarspoon.domain.features.remote

import com.sugarspoon.domain.base.BaseCoroutineTestWithTestDispatcherProvider
import com.sugarspoon.domain.model.remote.InflationResponseDTO
import com.sugarspoon.domain.repositories.ApiRepository
import com.sugarspoon.domain.usecase.remote.FetchDataOfInflationUseCase
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

class FetchDataOfInflationUseCaseTest : BaseCoroutineTestWithTestDispatcherProvider(
    dispatcher = StandardTestDispatcher()
) {

    @MockK
    private lateinit var repository: ApiRepository

    private lateinit var useCase: FetchDataOfInflationUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        useCase = FetchDataOfInflationUseCase(repository)
    }

    @Test
    fun `invoke calls getDetail method of repository`() = runTest {
        val expectedResult = flowOf(InflationResponseDTO(listOf()))
        coEvery { repository.getInflation(country = "Brazil") } returns expectedResult

        val actualResult = useCase.invoke("Brazil")

        coVerify(exactly = 1) {
            repository.getInflation("Brazil")
        }

        expectThat(actualResult).isEqualTo(expectedResult)
    }
}