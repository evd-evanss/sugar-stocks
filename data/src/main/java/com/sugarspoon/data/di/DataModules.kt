package com.sugarspoon.data.di

import com.sugarspoon.data.repositories.FoundsRepository
import com.sugarspoon.data.repositories.FoundsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn((SingletonComponent::class))
abstract class DataModules {
    
    @Binds
    abstract fun bindsRepository(repository: FoundsRepositoryImpl): FoundsRepository
}