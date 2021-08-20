package com.example.styletransferapp.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Singleton
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

//    provide db, dao, dto, etc.
}