package com.example.styletransferapp.representation.main.prev_pictures_screen

import com.example.styletransferapp.business.domain.utils.ImageDataHolder
import com.example.styletransferapp.business.domain.utils.ImageHolder
import com.example.styletransferapp.business.domain.utils.ImageType

sealed class UpdateGalleryEventState : ImageType {
    data class Remove(val imageDataHolder: ImageDataHolder, val onlyFromCache: Boolean) : UpdateGalleryEventState()
    data class AddOrReplace(val imageHolder: ImageHolder) : UpdateGalleryEventState()
}