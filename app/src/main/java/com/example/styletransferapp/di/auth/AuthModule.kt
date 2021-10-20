package com.example.styletransferapp.di.auth

import com.example.styletransferapp.business.interactors.ClearCacheAndSessionEvent
import com.example.styletransferapp.representation.auth.login_screen.data.LoginDataSource
import com.example.styletransferapp.representation.auth.login_screen.data.LoginRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class AuthModule {

    @Singleton
    @Provides
    fun provideLoginDataSource(): LoginDataSource
        = LoginDataSource()

    @Singleton
    @Provides
    fun provideLoginRepository(
        dataSource: LoginDataSource,
        clearCacheAndSessionEvent: ClearCacheAndSessionEvent
    ): LoginRepositoryImpl
        = LoginRepositoryImpl(dataSource, clearCacheAndSessionEvent)
}