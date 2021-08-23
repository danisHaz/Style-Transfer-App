package com.example.styletransferapp.business.services

import com.example.styletransferapp.business.domain.utils.ImageHolder
import com.example.styletransferapp.business.services.network.WebService
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    val webService: WebService

    suspend fun getGallery(userId: Int): Flow<ImageHolder>

    fun getGallerySize(userId: Int): Int
}