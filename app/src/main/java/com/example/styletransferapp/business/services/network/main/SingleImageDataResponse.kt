package com.example.styletransferapp.business.services.network.main

import com.example.styletransferapp.business.domain.utils.main.ImageDataHolder
import com.google.gson.annotations.SerializedName

class SingleImageDataResponse(
    @SerializedName("id")
    var imageId: Int,
    @SerializedName("url")
    var imageUrl: String?,
    @SerializedName("title")
    var title: String?,
    @SerializedName("description")
    var description: String?,
    @SerializedName("last_updated")
    var date: String?,
) {
    fun toImageDataHolder(): ImageDataHolder
        = ImageDataHolder(
            imageId,
            imageUrl,
            title,
            description,
            date,
        )
}
