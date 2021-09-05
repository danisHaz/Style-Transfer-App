package com.example.styletransferapp.business.services

interface AppDataStore {

    suspend fun setVal(key: String, value: String)

    suspend fun getVal(key: String): String?
}