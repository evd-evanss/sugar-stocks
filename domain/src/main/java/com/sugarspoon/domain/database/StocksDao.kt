package com.sugarspoon.domain.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.sugarspoon.domain.model.local.SummaryStockEntity
import com.sugarspoon.domain.model.local.StockTable

@Dao
interface StocksDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(stock: SummaryStockEntity): Long

    @Query("UPDATE ${StockTable.name} SET code = :code, name = :name, sector = :sector, logo = :logo WHERE code = :code")
    suspend fun edit(code: String, name: String, sector: String, logo: String): Int

    @Transaction
    @Delete
    suspend fun delete(stock: SummaryStockEntity): Int

    @Transaction
    @Query("SELECT * FROM ${StockTable.name} WHERE code = :code")
    suspend fun findStock(code: String): SummaryStockEntity

    @Transaction
    @Query("SELECT * FROM ${StockTable.name}")
    suspend fun loadAllStocks(): List<SummaryStockEntity>

}