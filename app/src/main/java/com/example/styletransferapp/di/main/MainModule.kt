package com.example.styletransferapp.di.main

import com.example.styletransferapp.business.domain.utils.SessionManager
import com.example.styletransferapp.business.interactors.ClearCacheAndSessionEvent
import com.example.styletransferapp.business.interactors.GetImagesEvent
import com.example.styletransferapp.business.interactors.UpdateGalleryEvent
import com.example.styletransferapp.business.services.AppRepository
import com.example.styletransferapp.representation.main.prev_pictures_screen.UpdateGalleryEventState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(ViewModelComponent::class)
object MainModule {

    @ViewModelScoped
    @Provides
    fun provideUpdateGalleryEvent(
        sessionManager: SessionManager,
        repo: AppRepository,
    ) = UpdateGalleryEvent(sessionManager, repo)

    @ViewModelScoped
    @Provides
    fun provideClearCacheAndSessionEvent(
        sessionManager: SessionManager,
        repo: AppRepository,
    ) = ClearCacheAndSessionEvent(sessionManager, repo)

    @ViewModelScoped
    @Provides
    fun provideGetImagesEvent(
        sessionManager: SessionManager,
        repo: AppRepository,
    ) = GetImagesEvent(sessionManager, repo)

}