package com.sugarspoon.domain.usecase.remote

import com.sugarspoon.domain.repositories.ApiRepository
import java.lang.Exception
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext

class FetchDataOfInflationUseCase @Inject constructor(
    private val repository: ApiRepository
) {

    suspend fun invoke(
        country: String,
        historical: Boolean = false,
        start: String = "",
        end: String = ""
    ) = Result.runCatching {
        withContext(Dispatchers.Default) {
            val flow = repository.getInflation(country, historical, start, end)
            flow.map {
                success(it.inflation.first())
            }.catch {
                emit(Result.failure(Exception("Falha ao buscar dados de inflação")))
            }.onStart {
                val storedData = flow.first().inflation.first()
                emit(Result.success(storedData))
            }.distinctUntilChanged()
        }
    }
}