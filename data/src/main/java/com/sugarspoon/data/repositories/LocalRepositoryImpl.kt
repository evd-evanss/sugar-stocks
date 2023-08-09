package com.sugarspoon.data.repositories

import com.sugarspoon.data.sources.LocalDataSource
import com.sugarspoon.domain.model.local.SummaryStockEntity
import com.sugarspoon.domain.repositories.LocalRepository
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val dataSource: LocalDataSource
) : LocalRepository {

    override suspend fun save(summaryStockEntity: SummaryStockEntity) = dataSource.save(summaryStockEntity)

    override suspend fun delete(code: String) = dataSource.delete(code)

    override suspend fun edit(summaryStockEntity: SummaryStockEntity) = dataSource.edit(summaryStockEntity)

    override suspend fun findNote(code: String) = dataSource.findNote(code)

    override suspend fun getAllStocks() = dataSource.getAllStocks()

}