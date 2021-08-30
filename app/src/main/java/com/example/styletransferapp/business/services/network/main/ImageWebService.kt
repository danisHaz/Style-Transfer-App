package com.example.styletransferapp.business.services.network.main

import com.example.styletransferapp.business.services.persistence.main.ImageHolder

interface ImageWebService {
    val isImplemented: Boolean
        get() = false

    suspend fun getGallery(userId: Int): GalleryResponse
}