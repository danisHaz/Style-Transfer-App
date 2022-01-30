package com.example.styletransferapp.di

import com.example.styletransferapp.business.domain.utils.SessionManager
import com.example.styletransferapp.business.services.persistence.AppDatabase
import com.example.styletransferapp.business.services.persistence.main.ImageDao
import com.example.styletransferapp.utils.Constants

import com.google.gson.Gson
import com.google.gson.GsonBuilder

import android.app.Application

import androidx.room.Room

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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

    @RetrofitBaseUrl
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit
        = Retrofit.Builder()
        .baseUrl(Constants.baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @RetrofitApiUrl
    @Provides
    fun provideRetrofitApi(gson: Gson): Retrofit
        = Retrofit.Builder()
        .baseUrl(Constants.ApiUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Singleton
    @Provides
    fun provideGson(): Gson
        = GsonBuilder().create()
}