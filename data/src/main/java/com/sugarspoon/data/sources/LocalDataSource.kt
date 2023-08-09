package com.sugarspoon.data.sources

import com.sugarspoon.domain.model.local.SummaryStockEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun save(summaryStockEntity: SummaryStockEntity): Long

    suspend fun delete(code: String): Int

    suspend fun edit(summaryStockEntity: SummaryStockEntity): Int

    suspend fun findNote(code: String): Flow<SummaryStockEntity>

    suspend fun getAllStocks(): Flow<List<SummaryStockEntity>>
}