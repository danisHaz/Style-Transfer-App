package com.example.styletransferapp.business.services.persistence.main

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.styletransferapp.business.domain.utils.ImageDataHolder

@Entity(tableName="image_table")
class ImageHolderEntity(
    @PrimaryKey
    var imageId: Int,
    @ColumnInfo(name="image")
    var imageUrl: String?,
    @ColumnInfo(name="title")
    var title: String?,
    @ColumnInfo(name="description")
    var description: String?,
    @ColumnInfo(name="date")
    var dateUpdated: String?,
) {
    fun toImageHolder(): ImageDataHolder
        = ImageDataHolder(
            imageId,
            imageUrl,
            title,
            description,
            dateUpdated
        )
}