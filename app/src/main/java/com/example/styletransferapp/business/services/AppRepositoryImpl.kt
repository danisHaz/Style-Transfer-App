package com.example.styletransferapp.business.services

import com.example.styletransferapp.business.domain.utils.ImageDataHolder
import com.example.styletransferapp.business.services.network.main.ImageWebService
import com.example.styletransferapp.business.services.persistence.main.ImageDao
import com.example.styletransferapp.business.services.persistence.main.ImageHolderEntity

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import javax.inject.Inject

//-- TODO: set actual implementation for this class
class AppRepositoryImpl : AppRepository {

    @Inject override lateinit var imageWebService: ImageWebService
    @Inject override lateinit var imageDao: ImageDao

    private var gallerySize: Int = 0

    // TODO: remove this extension and add separated class as holder for ImageDataHolder List
    private fun List<ImageDataHolder>.toImageHolderEntityList(): List<ImageHolderEntity> {
        val imageHolderEntityList = mutableListOf<ImageHolderEntity>()
        forEach {
            imageHolderEntityList.add(it.toImageHolderEntity())
        }
        return imageHolderEntityList
    }

    override fun getGallery(userId: Int): Flow<ImageDataHolder>
        = flow {
            var serverImages: List<ImageDataHolder>? = null
            //-- TODO: handle errors
            if (imageWebService.isImplemented)
                serverImages = imageWebService
                    .getGallery(userId)
                    ?.toImageHolderList()

            serverImages?.let {
                imageDao.updateCache(
                    it.toImageHolderEntityList()
                )
                gallerySize = it.size
            }

            imageDao.getGallery().forEach { imageHolder ->
                emit(imageHolder.toImageHolder())
            }
        }

    override fun getGallerySize(userId: Int): Int
        = gallerySize

    override suspend fun removeFromGallery(
        userId: Int,
        imageDataHolder: ImageDataHolder,
        onlyFromCache: Boolean
    ) {
        if (onlyFromCache) {
            imageDao.removeFromGallery(imageDataHolder.imageId)
            return
        }
        imageWebService.removeFromGalleryById(userId, imageDataHolder.imageId)
    }

    override suspend fun addToGalleryOrReplace(userId: Int, imageDataHolder: ImageDataHolder) {
        imageWebService.addToGalleryOrReplace(userId, imageDataHolder)
        imageDao.addToGalleryOrReplace(imageDataHolder.toImageHolderEntity())
    }

    override suspend fun clearCache(userId: Int) {
        imageDao.clearCache()
    }
}
