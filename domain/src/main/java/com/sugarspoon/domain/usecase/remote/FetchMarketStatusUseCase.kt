package com.sugarspoon.domain.usecase.remote

import com.sugarspoon.domain.repositories.ApiRepository
import javax.inject.Inject

class FetchMarketStatusUseCase @Inject constructor(
    private val repository: ApiRepository
) {

    fun invoke() = repository.getMarketStatus()

}