package com.sugarspoon.data.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sugarspoon.data.database.StockTable

@Entity(tableName = StockTable.name)
data class StockEntity(
    @PrimaryKey
    val code: String,
    val name: String,
    val sector: String,
    val logo: String
)
