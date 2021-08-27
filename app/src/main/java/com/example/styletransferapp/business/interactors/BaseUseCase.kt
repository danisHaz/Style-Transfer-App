package com.example.styletransferapp.business.interactors

import com.example.styletransferapp.business.domain.utils.DataState
import kotlinx.coroutines.flow.Flow

abstract class BaseUseCase<
        Request : BaseUseCase.RequestType,
        Response : BaseUseCase.ResponseType
> {

    var requestData: Request? = null

    abstract fun <Response> execute(): Flow<DataState>

    interface RequestType
    interface ResponseType
}