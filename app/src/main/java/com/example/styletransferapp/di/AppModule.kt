package com.example.styletransferapp.di

import com.example.styletransferapp.business.services.AppRepository
import com.example.styletransferapp.business.services.AppRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Singleton
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
//    provide db, dao, dto, etc.

    @Singleton
    @Provides
    fun provideAppRepository(): AppRepository
        = AppRepositoryImpl()
}