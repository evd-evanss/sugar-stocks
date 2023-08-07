package com.sugarspoon.data.di

import android.content.Context
import androidx.room.Room
import com.sugarspoon.data.database.AppDataBase
import com.sugarspoon.data.database.StockTable
import com.sugarspoon.data.database.StocksDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ProvidesModule {

    @Provides
    fun providesDataBaseBuilder(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context = context,
            AppDataBase::class.java,
            name = StockTable.name
        ).build()

    @Provides
    fun providesNotesDao(appDataBase: AppDataBase) : StocksDao {
        return appDataBase.stockDao()
    }
}