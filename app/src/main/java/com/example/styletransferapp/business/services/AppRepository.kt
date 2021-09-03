package com.example.styletransferapp.business.services

import com.example.styletransferapp.business.domain.utils.ImageDataHolder
import com.example.styletransferapp.business.services.network.main.ImageWebService
import com.example.styletransferapp.business.services.persistence.main.ImageDao
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    var imageWebService: ImageWebService
    var imageDao: ImageDao

    fun getGallery(userId: Int): Flow<ImageDataHolder>

    fun getGallerySize(userId: Int): Int

    suspend fun addToGalleryOrReplace(userId: Int, imageHolder: ImageDataHolder)

    suspend fun removeFromGallery(userId: Int, imageHolder: ImageDataHolder, onlyFromCache: Boolean)
}