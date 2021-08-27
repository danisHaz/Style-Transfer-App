package com.example.styletransferapp.business.services

import com.example.styletransferapp.business.domain.utils.ImageHolder
import com.example.styletransferapp.business.services.network.WebService
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    var webService: WebService

    suspend fun getGallery(userId: Int): Flow<ImageHolder>
    //-- actually this method sets constraint for strong
    // ordering of pictures in gallery
    suspend fun getImageByPosition(position: Int): ImageHolder

    fun getGallerySize(userId: Int): Int
}