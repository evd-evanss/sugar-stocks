package com.sugarspoon.domain.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = StockTable.name)
data class SummaryStockEntity(
    @PrimaryKey
    val code: String,
    val name: String,
    val sector: String,
    val logo: String
)
