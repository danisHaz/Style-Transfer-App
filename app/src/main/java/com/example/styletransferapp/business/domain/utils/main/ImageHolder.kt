package com.example.styletransferapp.business.domain.utils.main

import android.graphics.Bitmap
import com.example.styletransferapp.business.domain.utils.main.ImageDataHolder
import com.example.styletransferapp.business.interactors.BaseUseCase

class ImageHolder(
    val imageId: Int,
    val imageUrl: String,
    val title: String,
    val description: String?,
    val dateUpdated: String?,
    val image: Bitmap,
) : BaseUseCase.ResponseType {
    fun toImageDataHolder(): ImageDataHolder
        = ImageDataHolder(
            imageId,
            imageUrl,
            title,
            description,
            dateUpdated,
        )
}