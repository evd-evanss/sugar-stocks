package com.sugarspoon.data.repositories

import com.sugarspoon.data.domain.entity.StockEntity
import com.sugarspoon.data.sources.LocalDataSource
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val dataSource: LocalDataSource
) : LocalRepository {

    override suspend fun save(stockEntity: StockEntity) = dataSource.save(stockEntity)

    override suspend fun delete(code: String) = dataSource.delete(code)

    override suspend fun edit(stockEntity: StockEntity) = dataSource.edit(stockEntity)

    override suspend fun findNote(code: String) = dataSource.findNote(code)

    override suspend fun getAllStocks() = dataSource.getAllStocks()

}