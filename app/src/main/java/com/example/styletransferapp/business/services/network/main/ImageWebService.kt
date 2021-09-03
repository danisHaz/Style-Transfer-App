package com.example.styletransferapp.business.services.network.main

import com.example.styletransferapp.business.domain.utils.ImageDataHolder

interface ImageWebService {
    val isImplemented: Boolean
        get() = false

    suspend fun getGallery(userId: Int): GalleryResponse?
    suspend fun addToGalleryOrReplace(userId: Int, imageDataHolder: ImageDataHolder)
    suspend fun getImageDataById(userId: Int, imageId: Int): SingleImageDataResponse?
    suspend fun removeFromGalleryById(userId: Int, imageId: Int)
}