package com.example.styletransferapp.di

import com.example.styletransferapp.business.domain.utils.SessionManager
import com.example.styletransferapp.business.services.AppRepository
import com.example.styletransferapp.business.services.AppRepositoryImpl
import com.example.styletransferapp.business.services.persistence.AppDatabase
import com.example.styletransferapp.business.services.persistence.main.ImageDao

import android.app.Application

import androidx.room.Room

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAppRepository(): AppRepository
        = AppRepositoryImpl()

    @Singleton
    @Provides
    fun provideAppDatabase(context: Application): AppDatabase
        = Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.NAME)
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideImageDao(appDatabase: AppDatabase): ImageDao
        = appDatabase.getImageDao()

    @Singleton
    @Provides
    fun provideSessionManager(): SessionManager
        = SessionManager.manager
}