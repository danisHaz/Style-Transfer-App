package com.example.styletransferapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.styletransferapp.business.services.AppRepository
import com.example.styletransferapp.business.services.AppRepositoryImpl
import com.example.styletransferapp.business.services.persistence.AppDatabase
import com.example.styletransferapp.business.services.persistence.main.ImageDao
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

    @Singleton
    @Provides
    fun provideAppDatabase(context: Context): AppDatabase
        = Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.NAME)
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideImageDao(appDatabase: AppDatabase): ImageDao
        = appDatabase.getImageDao()
}