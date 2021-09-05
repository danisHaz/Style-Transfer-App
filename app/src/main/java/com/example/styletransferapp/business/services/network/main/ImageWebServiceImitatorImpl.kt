package com.example.styletransferapp.business.services.network.main

import com.example.styletransferapp.business.domain.utils.ImageDataHolder

/**
 * This class supposed to imitate network requests without actual implementation.
 * This means that it is only for testing main components without real network stuff.
 */
class ImageWebServiceImitatorImpl : ImageWebService {
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

    override suspend fun getGallery(userId: Int): GalleryResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getImageDataById(userId: Int, imageId: Int): SingleImageDataResponse? {
        remoteGallery.forEach {
            if (imageId == it.imageId)
                return SingleImageDataResponse(
                    imageId = it.imageId,
                    imageUrl = it.imageUrl,
                    title = it.title,
                    description = it.description,
                    date = it.dateUpdated
                )
        }
        return null
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