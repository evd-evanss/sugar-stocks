package com.sugarspoon.domain.usecase.local

import com.sugarspoon.domain.model.local.SummaryStockEntity
import com.sugarspoon.domain.repositories.LocalRepository
import javax.inject.Inject

class SavePreferenceUseCase @Inject constructor(
    private val repository: LocalRepository
) {

    suspend fun invoke(data: SummaryStockEntity) = repository.save(data)
}