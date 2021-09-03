package com.example.styletransferapp.business.domain.utils

import com.example.styletransferapp.business.interactors.BaseUseCase

data class ImageDataHolder(
    val imageId: Int,
    val imageUrl: String?,
    val title: String?,
    val description: String?,
    val dateUpdated: String?,
) : BaseUseCase.RequestType, BaseUseCase.ResponseType