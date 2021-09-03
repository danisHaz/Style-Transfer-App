package com.example.styletransferapp.business.domain.utils

sealed class DataState {
    data class Data<DataType>(val data: DataType, val message: String? = null) : DataState()
    data class Success(val message: String?) : DataState()
    data class Error(val message: String? = null) : DataState()
    object Loading : DataState()
}