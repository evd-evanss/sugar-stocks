package com.sugarspoon.domain.usecase.local

import com.sugarspoon.domain.repositories.LocalRepository
import javax.inject.Inject

class GetAllPreferenceUseCase @Inject constructor(
    private val repository: LocalRepository
) {

    suspend fun invoke() = repository.getAllStocks()
}