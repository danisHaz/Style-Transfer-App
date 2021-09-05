package com.example.styletransferapp.business.interactors

import com.example.styletransferapp.business.domain.utils.DataState
import com.example.styletransferapp.business.domain.utils.SessionManager
import com.example.styletransferapp.business.services.AppRepository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.catch

import javax.inject.Inject

class ClearCacheAndSessionEvent
@Inject
constructor(
    private val sessionManager: SessionManager,
    private val repo: AppRepository,
) : BaseUseCase<BaseUseCase.RequestType, BaseUseCase.ResponseType>() {

    companion object {
        const val ON_SUCCESS = "Cache clear success"
        const val ON_ERROR = "Cache clear failure"
    }

    override fun execute(data: RequestType?): Flow<DataState>
        = flow {
            emit(DataState.Loading)
            repo.clearCache(sessionManager.data.userId)
            emit(DataState.Success(ON_SUCCESS))
        }.catch { e ->
            emit(DataState.Error("$ON_ERROR: $e"))
        }
}