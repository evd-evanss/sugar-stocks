package com.sugarspoon.domain.usecase.remote

import com.sugarspoon.domain.repositories.ApiRepository
import javax.inject.Inject

class FetchDetailUseCase @Inject constructor(
    private val repository: ApiRepository
) {

    suspend fun invoke(tickers: String, range: String = "", interval: String = "") =
        repository.getDetail(tickers, range, interval)
}