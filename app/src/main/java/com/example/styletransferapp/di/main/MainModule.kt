package com.example.styletransferapp.di.main

import com.example.styletransferapp.business.domain.utils.SessionManager
import com.example.styletransferapp.business.interactors.ClearCacheAndSessionEvent
import com.example.styletransferapp.business.interactors.GetImagesEvent
import com.example.styletransferapp.business.interactors.UpdateGalleryEvent
import com.example.styletransferapp.business.services.main.AppRepository
import com.example.styletransferapp.business.services.main.AppRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object MainModule {

    @Provides
    fun provideAppRepository(): AppRepository
        = AppRepositoryImpl()

    @Provides
    fun provideUpdateGalleryEvent(
        sessionManager: SessionManager,
        repo: AppRepository,
    ) = UpdateGalleryEvent(sessionManager, repo)

    @Provides
    fun provideClearCacheAndSessionEvent(
        sessionManager: SessionManager,
        repo: AppRepository,
    ) = ClearCacheAndSessionEvent(sessionManager, repo)

    @Provides
    fun provideGetImagesEvent(repo: AppRepository)
        = GetImagesEvent(repo)
}