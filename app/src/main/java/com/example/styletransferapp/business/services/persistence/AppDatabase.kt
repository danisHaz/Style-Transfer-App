package com.example.styletransferapp.business.services.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.styletransferapp.business.services.persistence.main.ImageDao

@Database(entities=[], version=1, exportSchema=false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getImageDao(): ImageDao

    companion object {
        const val NAME = "app_database"
    }
}