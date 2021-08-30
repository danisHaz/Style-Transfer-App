package com.example.styletransferapp.business.services.network.main

import android.graphics.Bitmap
import com.example.styletransferapp.business.services.persistence.main.ImageHolder
import com.google.gson.annotations.SerializedName

data class GalleryResponse(
    @SerializedName("gallery")
    var gallery: List<Bitmap>
)

fun toImageHolderList(response: GalleryResponse)
    = List(response.gallery.size) { index ->
        ImageHolder(response.gallery[index], index)
    }