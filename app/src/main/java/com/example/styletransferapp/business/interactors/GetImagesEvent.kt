package com.example.styletransferapp.business.interactors

import com.example.styletransferapp.business.domain.utils.DataState
import com.example.styletransferapp.business.domain.utils.SessionManager
import com.example.styletransferapp.business.services.AppRepository
import com.example.styletransferapp.business.domain.utils.main.ImageDataHolder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetImagesEvent
@Inject
constructor(
    private val repo: AppRepository,
) : BaseUseCase<SessionManager.SessionData, ImageDataHolder>() {
    companion object {
        const val ON_SUCCESS: String
            = "Successful loading from repository"
        const val ON_SINGLE_QUERY_SUCCESS: String
            = "New ImageDataHolder emitted"
        const val ON_ERROR: String
            = "Error fetching data in GetImagesEvent"
        const val ON_ERROR_NOTHING_SENT_MESSAGE: String
            = "Neither error nor success were sent from getGallery()"
        const val ON_INVALID_DATA_PROVIDED: String
            = "Null of invalid data provided"
    }

    override fun execute(data: SessionManager.SessionData?): Flow<DataState> = flow {
        if (data == null) {
            emit(DataState.Error(ON_INVALID_DATA_PROVIDED))
            return@flow
        }

        emit(DataState.Loading)
        repo.getGallery(data.userId).collect { dataState ->
            when (dataState) {
                is DataState.Data<*> -> {
                    emit(DataState.Data(dataState.data, ON_SINGLE_QUERY_SUCCESS))
                }
                is DataState.Success -> emit(DataState.Success(ON_SUCCESS))
                is DataState.Error -> emit(dataState)
            }
        }
        emit(DataState.Error(ON_ERROR_NOTHING_SENT_MESSAGE))
    }.catch { e ->
        emit(DataState.Error("$ON_ERROR: ${e.message}"))
    }
}