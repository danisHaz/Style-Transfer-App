package com.example.styletransferapp.business.services.main

import com.example.styletransferapp.business.domain.utils.DataState
import com.example.styletransferapp.business.domain.utils.main.ImageDataHolder
import com.example.styletransferapp.business.services.network.main.ImageWebService
import com.example.styletransferapp.business.services.persistence.main.ImageDao
import com.example.styletransferapp.business.services.persistence.main.ImageHolderEntity
import com.example.styletransferapp.business.domain.utils.Result

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

import javax.inject.Inject

//-- TODO: set actual implementation for this class
class AppRepositoryImpl : AppRepository {

    companion object {
        const val ON_INVALID_DATA_ERROR: String
            = "UserId is null"
        const val ON_SUCCESS: String
            = "Query successful"
        const val ON_RETRIEVED_DATA_NULL_ERROR
            = "Sent data with userId is null"
        const val ON_UNKNOWN_ERROR
            = "Unknown error: "
    }

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

    override fun getGallery(userId: Int): Flow<DataState> = flow {
        emit(DataState.Loading)

        var serverImages: List<ImageDataHolder>? = null
        //-- TODO: handle errors
        if (imageWebService.isImplemented)
            serverImages = imageWebService
                .getGallery(userId).let gallery@ {
                    if (it is Result.Success)
                        if (it.data == null) {
                            emit(DataState.Error(ON_RETRIEVED_DATA_NULL_ERROR))
                            return@flow
                        } else
                            return@gallery it.data.toImageHolderList()
                    //-- => Result.Error
                    //-- here's actually problem with user notification because it's needed
                    //-- to send notification (probably with another function)
                    return@gallery null
                }

        serverImages?.let {
            imageDao.updateCache(
                it.toImageHolderEntityList()
            )
        }

        imageDao.getGallery().forEach { imageHolder ->
            emit(DataState.Data(imageHolder.toImageHolder()))
        }
        gallerySize = imageDao.getGallerySize()

        emit(DataState.Success(ON_SUCCESS))
    }.catch { e ->
        emit(DataState.Error())
    }

    override fun getGallerySize(userId: Int): Int
        = gallerySize

    override suspend fun removeFromGallery(
        userId: Int,
        imageDataHolder: ImageDataHolder,
        onlyFromCache: Boolean,
    ) {
        imageDao.removeFromGallery(imageDataHolder.imageId)
        gallerySize = imageDao.getGallerySize()
        if (!onlyFromCache)
            imageWebService.removeFromGalleryById(userId, imageDataHolder.imageId)
    }

    override suspend fun addToGalleryOrReplace(userId: Int, imageDataHolder: ImageDataHolder) {
        imageWebService.addToGalleryOrReplace(userId, imageDataHolder)
        imageDao.addToGalleryOrReplace(imageDataHolder.toImageHolderEntity())
        gallerySize = imageDao.getGallerySize()
    }

    override suspend fun clearCache(userId: Int) {
        imageDao.clearCache()
        gallerySize = imageDao.getGallerySize()
    }
}
