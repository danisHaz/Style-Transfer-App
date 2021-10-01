package com.example.styletransferapp.business.interactors

import com.example.styletransferapp.business.domain.utils.DataState
import com.example.styletransferapp.business.domain.utils.SessionManager
import com.example.styletransferapp.business.services.AppRepository
import com.example.styletransferapp.business.domain.utils.ImageDataHolder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetImagesEvent
@Inject
constructor(
    private val sessionManager: SessionManager,
    private val repo: AppRepository,
) : BaseUseCase<SessionManager.SessionData, ImageDataHolder>() {
    companion object {
        private const val ON_SUCCESS = "Successful loading from repository"
        private const val ON_SINGLE_QUERY_SUCCESS = "New ImageDataHolder emitted"
        private const val ON_ERROR = "Error fetching data in GetImagesEvent"
    }

    override fun execute(data: SessionManager.SessionData?): Flow<DataState>
        = flow {
            emit(DataState.Loading)
            repo.getGallery(sessionManager.data.userId).collect {
                emit(DataState.Data(it, ON_SINGLE_QUERY_SUCCESS))
            }
            emit(DataState.Success(ON_SUCCESS))
        }.catch { e ->
            emit(DataState.Error("$ON_ERROR: ${e.message}"))
        }
}