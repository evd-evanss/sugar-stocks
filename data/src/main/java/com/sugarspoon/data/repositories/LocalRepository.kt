package com.sugarspoon.data.repositories

import com.sugarspoon.data.domain.entity.StockEntity
import kotlinx.coroutines.flow.Flow

interface LocalRepository {

    suspend fun save(stockEntity: StockEntity): Long

    suspend fun delete(code: String): Int

    suspend fun edit(stockEntity: StockEntity): Int

    suspend fun findNote(code: String): Flow<StockEntity>

    suspend fun getAllStocks(): Flow<List<StockEntity>>
}