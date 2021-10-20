package com.example.styletransferapp.business.domain.utils

import com.example.styletransferapp.business.interactors.BaseUseCase

sealed class DataState : BaseUseCase.ResponseType {
    data class Data<DataType>(val data: DataType, val message: String? = null) : DataState()
    data class Success(val message: String?) : DataState()
    data class Error(val message: String? = null) : DataState()
    object Loading : DataState()
}