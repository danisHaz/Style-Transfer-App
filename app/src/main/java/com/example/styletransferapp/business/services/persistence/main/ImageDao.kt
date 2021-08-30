package com.example.styletransferapp.business.services.persistence.main

import androidx.room.*

@Dao
interface ImageDao {

    @Query("SELECT * FROM image_table")
    suspend fun getGallery(): List<ImageHolder>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToGalleryOrReplace(holder: ImageHolder)

    @Query("DELETE FROM image_table WHERE _imageId = :id")
    suspend fun removeFromGallery(id: Int)
}