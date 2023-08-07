package com.sugarspoon.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.sugarspoon.data.domain.entity.StockEntity

@Dao
interface StocksDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(stock: StockEntity): Long

    @Query("UPDATE ${StockTable.name} SET code = :code, name = :name, sector = :sector, logo = :logo WHERE code = :code")
    suspend fun edit(code: String, name: String, sector: String, logo: String): Int

    @Transaction
    @Delete
    suspend fun delete(stock: StockEntity): Int

    @Transaction
    @Query("SELECT * FROM ${StockTable.name} WHERE code = :code")
    suspend fun findStock(code: String): StockEntity

    @Transaction
    @Query("SELECT * FROM ${StockTable.name}")
    suspend fun loadAllStocks(): List<StockEntity>

}