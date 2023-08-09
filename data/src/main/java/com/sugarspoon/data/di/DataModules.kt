package com.sugarspoon.data.di

import com.sugarspoon.data.repositories.ApiRepositoryImpl
import com.sugarspoon.data.repositories.LocalRepositoryImpl
import com.sugarspoon.data.sources.LocalDataSource
import com.sugarspoon.data.sources.LocalDataSourceImpl
import com.sugarspoon.domain.repositories.ApiRepository
import com.sugarspoon.domain.repositories.LocalRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn((SingletonComponent::class))
abstract class DataModules {

    @Binds
    abstract fun bindsApiRepository(repository: ApiRepositoryImpl): ApiRepository

    @Binds
    abstract fun bindsLocalRepository(repository: LocalRepositoryImpl): LocalRepository

    @Binds
    abstract fun bindsLocalDataSource(dataSourceImpl: LocalDataSourceImpl): LocalDataSource
}