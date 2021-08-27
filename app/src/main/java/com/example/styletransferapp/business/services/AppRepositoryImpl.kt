package com.example.styletransferapp.business.services

import com.example.styletransferapp.business.domain.utils.ImageHolder
import com.example.styletransferapp.business.services.network.WebService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

//-- TODO: set actual implementation for this class
class AppRepositoryImpl : AppRepository {

    @Inject override lateinit var webService: WebService

    override suspend fun getGallery(userId: Int): Flow<ImageHolder>
        = flow {}

    override suspend fun getImageByPosition(position: Int): ImageHolder {
        return ImageHolder()
    }

    override fun getGallerySize(userId: Int): Int
        = 10
}