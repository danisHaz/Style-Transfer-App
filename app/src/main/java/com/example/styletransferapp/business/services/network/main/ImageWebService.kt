package com.example.styletransferapp.business.services.network.main

import com.example.styletransferapp.business.domain.utils.main.ImageDataHolder
import com.example.styletransferapp.business.domain.utils.Result

interface ImageWebService {

    val isImplemented: Boolean
        get() = false

    suspend fun getGallery(userId: Int): Result<GalleryResponse>
    suspend fun addToGalleryOrReplace(userId: Int, imageDataHolder: ImageDataHolder)
    suspend fun getImageDataById(userId: Int, imageId: Int): Result<SingleImageDataResponse>
    suspend fun removeFromGalleryById(userId: Int, imageId: Int)
}