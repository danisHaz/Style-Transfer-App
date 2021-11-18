package com.example.styletransferapp.di.auth

import com.example.styletransferapp.business.interactors.LoginEvent
import com.example.styletransferapp.business.services.network.auth.AuthService
import com.example.styletransferapp.business.services.auth.LoginRepository
import com.example.styletransferapp.business.services.auth.LoginRepositoryImpl

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import retrofit2.Retrofit

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {


    @Singleton
    @Provides
    fun provideLoginRepository(
        authService: AuthService
    ): LoginRepository
        = LoginRepositoryImpl(authService)

    @Singleton
    @Provides
    fun provideAuthService(retrofit: Retrofit): AuthService
        = retrofit.create(AuthService::class.java)

    @Provides
    fun provideLoginEvent(
        repository: LoginRepository,
    ) = LoginEvent(repository)

}