package com.example.styletransferapp.business.services.persistence.main

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.styletransferapp.business.interactors.BaseUseCase

@Entity(tableName="image_table")
class ImageHolder() : BaseUseCase.ResponseType {
    constructor(
        bitmap: Bitmap,
        imageId: Int,
    ) : this() {
        _bitmap = bitmap
        _imageId = imageId
    }

    @ColumnInfo(name="image")
    private lateinit var _bitmap: Bitmap
    val image: Bitmap
        get() = _bitmap

    @PrimaryKey
    private var _imageId: Int = 0
    val id: Int
        get() = _imageId
}