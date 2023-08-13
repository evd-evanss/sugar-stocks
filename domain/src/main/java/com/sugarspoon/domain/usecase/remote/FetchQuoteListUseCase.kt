package com.sugarspoon.domain.usecase.remote

import com.sugarspoon.domain.repositories.ApiRepository
import javax.inject.Inject

class FetchQuoteListUseCase @Inject constructor(
    private val repository: ApiRepository
) {

    suspend fun invoke() = repository.getApiData()
}