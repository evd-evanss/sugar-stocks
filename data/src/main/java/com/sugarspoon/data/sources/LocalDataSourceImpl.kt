package com.sugarspoon.data.sources

import com.sugarspoon.domain.database.StocksDao
import com.sugarspoon.domain.model.local.SummaryStockEntity
import javax.inject.Inject
import kotlinx.coroutines.flow.flow

class LocalDataSourceImpl @Inject constructor(
    private val stocksDao: StocksDao
) : LocalDataSource {

    override suspend fun save(summaryStockEntity: SummaryStockEntity) = stocksDao.save(summaryStockEntity)

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

    override suspend fun edit(summaryStockEntity: SummaryStockEntity): Int {
        var response = 0
        summaryStockEntity.code.let {
            response = stocksDao.edit(
                code = summaryStockEntity.code,
                name = summaryStockEntity.name,
                sector = summaryStockEntity.sector,
                logo = summaryStockEntity.logo
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