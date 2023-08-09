package com.sugarspoon.domain.usecase.local

import com.sugarspoon.domain.repositories.LocalRepository
import javax.inject.Inject

class FindPreferenceUseCase @Inject constructor(
    private val repository: LocalRepository
) {

    suspend fun invoke(code: String) = repository.findNote(code)
}