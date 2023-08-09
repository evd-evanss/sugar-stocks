package com.sugarspoon.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sugarspoon.domain.database.StocksDao
import com.sugarspoon.domain.model.local.SummaryStockEntity
import com.sugarspoon.domain.model.local.StockTable

@Database(
    version = StockTable.version,
    entities = [SummaryStockEntity::class],
    exportSchema = false
)
abstract class AppDataBase: RoomDatabase() {
    abstract fun stockDao(): StocksDao
}