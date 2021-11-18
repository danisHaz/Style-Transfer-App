package com.example.styletransferapp.business.interactors

import android.util.Log
import com.example.styletransferapp.business.domain.utils.DataState
import com.example.styletransferapp.business.domain.utils.SessionManager
import com.example.styletransferapp.business.domain.utils.SessionState
import com.example.styletransferapp.business.services.main.AppRepository

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
        val NAME: String = this::class.java.name
        const val ON_SUCCESS = "Cache clear success"
        const val ON_ERROR = "Cache clear failure"
        const val ON_NON_NULLABLE_DATA_PROVIDED_ERROR
            = "Non null data was provided to execute()" +
                " method of ClearCacheAndSessionEvent, " +
                "but it is not used"
        const val ON_SESSION_DATA_NULL_ERROR: String
            = "Session data is null but it should not be"
    }

    override fun execute(data: RequestType?): Flow<DataState> = flow {
        if (data != null)
            //-- todo: think about logging and warning datastate stuff (check it in notion)
            Log.w(NAME, ON_NON_NULLABLE_DATA_PROVIDED_ERROR)
        emit(DataState.Loading)

        sessionManager.data?.let {
            repo.clearCache(it.userId)
            sessionManager.handleSessionUpdate(SessionState.Logout)
        } ?: emit(DataState.Error(ON_SESSION_DATA_NULL_ERROR))

        emit(DataState.Success(ON_SUCCESS))
    }.catch { e ->
        emit(DataState.Error("$ON_ERROR: $e"))
    }
}