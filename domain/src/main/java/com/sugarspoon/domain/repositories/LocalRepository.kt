package com.sugarspoon.domain.repositories

import com.sugarspoon.domain.model.local.SummaryStockEntity
import kotlinx.coroutines.flow.Flow

interface LocalRepository {

    suspend fun save(summaryStockEntity: SummaryStockEntity): Long

    suspend fun delete(code: String): Int

    suspend fun edit(summaryStockEntity: SummaryStockEntity): Int

    suspend fun findNote(code: String): Flow<SummaryStockEntity>

    suspend fun getAllStocks(): Flow<List<SummaryStockEntity>>
}