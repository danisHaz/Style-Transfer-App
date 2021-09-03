package com.example.styletransferapp.business.services.network.main

import com.example.styletransferapp.business.domain.utils.ImageDataHolder
import com.google.gson.annotations.SerializedName

class GalleryResponse(
    @SerializedName("ids")
    var imageIds: List<Int>,
    @SerializedName("gallery_urls")
    var galleryUrls: List<String?>,
    @SerializedName("titles")
    var titles: List<String?>,
    @SerializedName("descriptions")
    var descriptions: List<String?>,
    @SerializedName("last_updates")
    var dates: List<String?>,
) {

    fun toImageHolderList()
        = List(imageIds.size) { index ->
            ImageDataHolder(
                imageIds[index],
                galleryUrls[index],
                titles[index],
                descriptions[index],
                dates[index],
            )
        }
}