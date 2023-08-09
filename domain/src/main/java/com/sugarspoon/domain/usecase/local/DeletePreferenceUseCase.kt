package com.sugarspoon.domain.usecase.local

import com.sugarspoon.domain.repositories.LocalRepository
import javax.inject.Inject

class DeletePreferenceUseCase @Inject constructor(
    private val repository: LocalRepository
) {

    suspend fun invoke(code: String) = repository.delete(code)
}