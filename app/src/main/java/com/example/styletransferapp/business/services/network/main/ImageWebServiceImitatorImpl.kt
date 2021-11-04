package com.example.styletransferapp.business.services.network.main

import com.example.styletransferapp.business.domain.utils.DataState
import com.example.styletransferapp.business.domain.utils.main.ImageDataHolder
import com.example.styletransferapp.business.domain.utils.Result
import java.lang.Exception

/**
 * This class supposed to imitate network requests without actual implementation.
 * This means that it is only for testing main components without real network stuff.
 */
class ImageWebServiceImitatorImpl : ImageWebService {
    companion object {
        const val DEFAULT_ERROR_MESSAGE: String
            = "Error occurred in ImageWebService's component"
    }
    private val remoteGallery: MutableList<ImageDataHolder>
        = mutableListOf()

    //-- TODO: initialize this implementation by fake images from random websites

    override suspend fun addToGalleryOrReplace(userId: Int, imageDataHolder: ImageDataHolder)
        = run breaker@ {
            var contains = false
            remoteGallery.forEachIndexed { index, it ->
                if (it.imageId == imageDataHolder.imageId) {
                    remoteGallery.add(index, imageDataHolder)
                    contains = true
                    return@breaker
                }
            }
            if (!contains)
                remoteGallery.add(imageDataHolder)
        }

    override suspend fun getGallery(userId: Int): Result<GalleryResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getImageDataById(userId: Int, imageId: Int): Result<SingleImageDataResponse> {
        remoteGallery.forEach {
            if (imageId == it.imageId)
                return Result.Success(SingleImageDataResponse(
                    imageId = it.imageId,
                    imageUrl = it.imageUrl,
                    title = it.title,
                    description = it.description,
                    date = it.dateUpdated
                ))
        }
        return Result.Error(Exception(DEFAULT_ERROR_MESSAGE))
    }

    override suspend fun removeFromGalleryById(userId: Int, imageId: Int) {
        for (pos in 0 until remoteGallery.size) {
            remoteGallery[pos].let {
                if (it.imageId == imageId) {
                    remoteGallery.removeAt(pos)
                    return
                }
            }
        }
    }
}