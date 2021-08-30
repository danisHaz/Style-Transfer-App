package com.example.styletransferapp.business.interactors

import com.example.styletransferapp.business.domain.utils.DataState
import com.example.styletransferapp.business.domain.utils.SessionManager
import com.example.styletransferapp.business.services.AppRepository
import com.example.styletransferapp.business.services.persistence.main.ImageHolder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class GetImagesEvent(
    private val repo: AppRepository,
    private val sessionManager: SessionManager,
) : BaseUseCase<SessionManager.SessionData, ImageHolder>() {

    override fun execute(): Flow<DataState>
        = flow {
            emit(DataState.Loading)
            repo.getGallery(sessionManager.data.userId).collect {
                emit(DataState.Success(it, "Successful loading from repository"))
            }
        }.catch { e ->
            emit(DataState.Error(e.toString()))
        }
}