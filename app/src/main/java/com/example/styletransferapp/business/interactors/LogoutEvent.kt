package com.example.styletransferapp.business.interactors

import com.example.styletransferapp.business.domain.utils.DataState
import com.example.styletransferapp.business.services.auth.LoginRepository
import com.example.styletransferapp.business.domain.utils.auth.LoginPassword
import com.example.styletransferapp.business.domain.utils.Result

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

import javax.inject.Inject

class LogoutEvent
@Inject
constructor(
    private val loginRepository: LoginRepository,
) : BaseUseCase<LoginPassword, BaseUseCase.ResponseType>() {

    companion object {
        const val NULL_DATA_ERROR: String
            = "Null data provided as loginPassword object"

        const val ON_SUCCESS: String
            = "Successful logout"

        const val ON_UNKNOWN_ERROR = "Undetermined error"

    }

    override fun execute(data: LoginPassword?): Flow<DataState> = flow {
        data?.let {
            emit(DataState.Loading)
            try {
                when (val result = loginRepository.logout(data)) {
                    is Result.Success -> {
                        emit(DataState.Success(ON_SUCCESS))
                    }
                    is Result.Error -> {
                        emit(DataState.Error(errorCode=result.errorCode))
                    }
                }
            } catch (e: java.lang.Exception) {
                emit(DataState.Error("$ON_UNKNOWN_ERROR=[${e.message}]"))
            }
        } ?: emit(DataState.Error(NULL_DATA_ERROR))


    }.catch { e ->
        emit(DataState.Error("Unknown error=${e.message}"))
    }
}