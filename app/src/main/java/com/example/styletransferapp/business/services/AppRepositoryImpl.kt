package com.example.styletransferapp.business.services

import com.example.styletransferapp.business.services.persistence.main.ImageHolder
import com.example.styletransferapp.business.services.network.main.ImageWebService
import com.example.styletransferapp.business.services.network.main.toImageHolderList
import com.example.styletransferapp.business.services.persistence.main.ImageDao

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

//-- TODO: set actual implementation for this class
class AppRepositoryImpl : AppRepository {

    @Inject override lateinit var imageWebService: ImageWebService
    @Inject override lateinit var imageDao: ImageDao

    override fun getGallery(userId: Int): Flow<ImageHolder>
        = flow {
            var serverImages: List<ImageHolder>? = null
            if (imageWebService.isImplemented)
                serverImages = toImageHolderList(imageWebService.getGallery(userId))

            serverImages?.forEach { imageHolder ->
                imageDao.addToGalleryOrReplace(imageHolder)
            }

            imageDao.getGallery().forEach { imageHolder ->
                emit(imageHolder)
            }
        }

    override suspend fun getImageByPosition(position: Int): ImageHolder {
        return ImageHolder()
    }

    override fun getGallerySize(userId: Int): Int
        = 10
}