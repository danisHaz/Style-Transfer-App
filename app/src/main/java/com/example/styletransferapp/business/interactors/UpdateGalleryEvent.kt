package com.example.styletransferapp.business.interactors

import com.example.styletransferapp.business.domain.utils.*
import com.example.styletransferapp.business.domain.utils.main.ImageType
import com.example.styletransferapp.business.services.AppRepository
import com.example.styletransferapp.representation.main.prev_pictures_screen.UpdateGalleryEventState

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.catch

import javax.inject.Inject

class UpdateGalleryEvent
@Inject
constructor(
    private val sessionManager: SessionManager,
    private val repo: AppRepository,
) : BaseUseCase<ImageType, BaseUseCase.ResponseType>() {

    companion object {
        const val ON_REQUEST_DATA_NOT_PROVIDED
            = "Additional request data not provided"
        const val ON_INVALID_DATA
            = "Invalid data on \"request data\" provided"

        const val ON_ERROR = "Error fetching data in UpdateGalleryEvent"
        const val ON_SESSION_DATA_NULL_ERROR
            = "Session data is null"
        const val ON_ADDITION_SUCCESS = "Successful addition to repository"
        const val ON_REMOVAL_SUCCESS = "Successful removal from repository"
    }

    override fun execute(data: ImageType?): Flow<DataState> = flow {
        emit(DataState.Loading)
        if (data == null) {
            emit(DataState.Error(ON_REQUEST_DATA_NOT_PROVIDED))
            return@flow
        }
        if (sessionManager.data == null) {
            emit(DataState.Error(ON_SESSION_DATA_NULL_ERROR))
            return@flow
        }
        requestData = data

        when (requestData) {
            is UpdateGalleryEventState.AddOrReplace -> {
                repo.addToGalleryOrReplace(
                    sessionManager.data!!.userId,
                    (requestData as UpdateGalleryEventState.AddOrReplace)
                        .imageHolder
                        .toImageDataHolder(),
                )
                emit(DataState.Success(ON_ADDITION_SUCCESS))
            }
            is UpdateGalleryEventState.Remove -> {
                repo.removeFromGallery(
                    sessionManager.data!!.userId,
                    (requestData as UpdateGalleryEventState.Remove)
                        .imageDataHolder,
                    (requestData as UpdateGalleryEventState.Remove)
                        .onlyFromCache,
                )
                emit(DataState.Success(ON_REMOVAL_SUCCESS))
            }
            //-- this branch should not be requested
            else -> emit(DataState.Error(ON_INVALID_DATA))
        }
    }.catch { e ->
        emit(DataState.Error("$ON_ERROR: $e"))
    }
}