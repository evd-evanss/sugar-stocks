package com.sugarspoon.network.di

import com.sugarspoon.network.interceptors.SessionManager
import com.sugarspoon.network.interceptors.SessionManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn((SingletonComponent::class))
abstract class NetworkModuleBinds {

    @Binds
    abstract fun bindsSessionManager(sessionManager: SessionManagerImpl): SessionManager
}