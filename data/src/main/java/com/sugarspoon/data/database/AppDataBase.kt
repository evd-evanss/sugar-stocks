package com.sugarspoon.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sugarspoon.data.domain.entity.StockEntity

@Database(
    version = StockTable.version,
    entities = [StockEntity::class],
    exportSchema = false
)
abstract class AppDataBase: RoomDatabase() {
    abstract fun stockDao(): StocksDao
}