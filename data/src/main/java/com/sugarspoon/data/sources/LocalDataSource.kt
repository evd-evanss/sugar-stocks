package com.sugarspoon.data.sources

import com.sugarspoon.data.domain.entity.StockEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun save(stockEntity: StockEntity): Long

    suspend fun delete(code: String): Int

    suspend fun edit(stockEntity: StockEntity): Int

    suspend fun findNote(code: String): Flow<StockEntity>

    suspend fun getAllStocks(): Flow<List<StockEntity>>
}