package com.example.styletransferapp.business.domain.utils.main

import com.example.styletransferapp.business.interactors.BaseUseCase
import com.example.styletransferapp.business.services.persistence.main.ImageHolderEntity

class ImageDataHolder(
    val imageId: Int,
    val imageUrl: String?,
    val title: String?,
    val description: String?,
    val dateUpdated: String?,
) : BaseUseCase.RequestType, BaseUseCase.ResponseType {
    fun toImageHolderEntity(): ImageHolderEntity
        = ImageHolderEntity(
            imageId=imageId,
            imageUrl=imageUrl,
            title=title,
            description=description,
            dateUpdated=dateUpdated,
        )
}