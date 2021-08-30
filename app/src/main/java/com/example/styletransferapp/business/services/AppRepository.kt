package com.example.styletransferapp.business.services

import com.example.styletransferapp.business.services.persistence.main.ImageHolder
import com.example.styletransferapp.business.services.network.main.ImageWebService
import com.example.styletransferapp.business.services.persistence.main.ImageDao
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    var imageWebService: ImageWebService
    var imageDao: ImageDao

    fun getGallery(userId: Int): Flow<ImageHolder>
    //-- actually this method sets constraint for strong
    // ordering of pictures in gallery
    suspend fun getImageByPosition(position: Int): ImageHolder

    fun getGallerySize(userId: Int): Int
}