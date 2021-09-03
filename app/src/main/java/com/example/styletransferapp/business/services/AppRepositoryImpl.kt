package com.example.styletransferapp.business.services

import com.example.styletransferapp.business.domain.utils.ImageDataHolder
import com.example.styletransferapp.business.services.network.main.ImageWebService
import com.example.styletransferapp.business.services.persistence.main.ImageDao

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import javax.inject.Inject

//-- TODO: set actual implementation for this class
class AppRepositoryImpl : AppRepository {

    @Inject override lateinit var imageWebService: ImageWebService
    @Inject override lateinit var imageDao: ImageDao

    private var gallerySize: Int = 0

    override fun getGallery(userId: Int): Flow<ImageDataHolder>
        = flow {
            var serverImages: List<ImageDataHolder>? = null
            //-- TODO: handle errors
            if (imageWebService.isImplemented)
                serverImages = imageWebService
                    .getGallery(userId)
                    ?.toImageHolderList()

            serverImages?.let {
                imageDao.updateCache(it)
                gallerySize = imageDao.getGallerySize()
            }

            imageDao.getGallery().forEach { imageHolder ->
                emit(imageHolder)
            }
        }

    override fun getGallerySize(userId: Int): Int
        = gallerySize

    override suspend fun addToGalleryOrReplace(userId: Int, imageHolder: ImageDataHolder) {
        imageWebService.addToGalleryOrReplace(userId, imageHolder)
        imageDao.addToGalleryOrReplace(imageHolder)
    }

    override suspend fun removeFromGallery(
        userId: Int,
        imageHolder: ImageDataHolder,
        onlyFromCache: Boolean
    ) {
        if (!onlyFromCache)
            imageWebService.removeFromGalleryById(userId, imageHolder.imageId)

        imageDao.removeFromGallery(imageHolder.imageId)
    }
}
