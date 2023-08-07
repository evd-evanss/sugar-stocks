package com.sugarspoon.data.sources

import com.sugarspoon.data.database.StocksDao
import com.sugarspoon.data.domain.entity.StockEntity
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LocalDataSourceImpl @Inject constructor(
    private val stocksDao: StocksDao
) : LocalDataSource {

    override suspend fun save(stockEntity: StockEntity) = stocksDao.save(stockEntity)

    override suspend fun delete(code: String): Int {
        var response = 0
        getAllStocks().collect { notes ->
            val note = notes.firstOrNull { it.code == code }
            note?.let {
                response = stocksDao.delete(it)
            }
        }
        return response
    }

    override suspend fun edit(stockEntity: StockEntity): Int {
        var response = 0
        stockEntity.code.let {
            response = stocksDao.edit(
                code = stockEntity.code,
                name = stockEntity.name,
                sector = stockEntity.sector,
                logo = stockEntity.logo
            )
        }
        return response
    }

    override suspend fun findNote(code: String) = flow {
        emit(stocksDao.findStock(code))
    }

    override suspend fun getAllStocks() = flow {
        emit(stocksDao.loadAllStocks())
    }
}