package com.example.styletransferapp.business.services.persistence.main

import androidx.room.*
import com.example.styletransferapp.business.domain.utils.ImageDataHolder

@Dao
abstract class ImageDao {

    @Query("SELECT * FROM image_table")
    abstract suspend fun getGallery(): List<ImageHolderEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addToGalleryOrReplace(holder: ImageHolderEntity)

    @Query("SELECT COUNT(*) FROM image_table")
    abstract suspend fun getGallerySize(): Int

    @Query("DELETE FROM image_table WHERE imageId = :id")
    abstract suspend fun removeFromGallery(id: Int)

    @Query("DELETE FROM image_table")
    abstract suspend fun clearCache()

    suspend fun updateCache(imageList: List<ImageHolderEntity>) {
        imageList.forEach {
            addToGalleryOrReplace(it)
        }
    }
}
