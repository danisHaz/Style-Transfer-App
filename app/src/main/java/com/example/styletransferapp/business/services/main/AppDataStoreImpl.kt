package com.example.styletransferapp.business.services.main

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

import kotlinx.coroutines.flow.first

class AppDataStoreImpl(
    private val context: Context
) : AppDataStore {

    companion object {
        const val APP_DATASTORE_NAME = "app_data_store"
    }

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(APP_DATASTORE_NAME)
    override suspend fun getVal(key: String): String?
        = context.dataStore.data.first()[stringPreferencesKey(key)]

    override suspend fun setVal(key: String, value: String) {
        context.dataStore.edit {
            it[stringPreferencesKey(key)] = value
        }
    }
}