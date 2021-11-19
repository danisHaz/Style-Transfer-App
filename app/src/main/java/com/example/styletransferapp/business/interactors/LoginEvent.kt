package com.example.styletransferapp.business.interactors

import android.util.Log
import com.example.styletransferapp.business.domain.utils.DataState
import com.example.styletransferapp.business.services.auth.LoginRepository
import com.example.styletransferapp.business.domain.utils.Result
import com.example.styletransferapp.business.services.network.auth.responses.User
import com.example.styletransferapp.business.domain.utils.auth.LoginPassword
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class LoginEvent
@Inject
constructor(
    private val loginRepository: LoginRepository,
) : BaseUseCase<LoginPassword, DataState.Data<*>>() {
    companion object {
        val NAME = this::class.java.name
        const val ON_SUCCESS: String = "Successful log in"
        const val ON_LOGIN_PASSWORD_NOT_PROVIDED
            = "Login and password not provided"
        const val ON_BAD_LOGIN_ERROR
            = "Incorrect login or password provided"
        const val ON_UNKNOWN_ERROR = "Undetermined error"
    }

    override fun execute(data: LoginPassword?): Flow<DataState> = flow {
        data?.let { loginPassword ->
            emit(DataState.Loading)
            try {
                when (val result = loginRepository.login(loginPassword)) {
                    is Result.Success -> {
                        emit(DataState.Data<User>(result.data!!))
                        emit(DataState.Success(ON_SUCCESS))
                    }
                    is Result.Error -> {
                        emit(DataState.Error(errorCode=result.errorCode))
                    }
                }

            } catch (e: Exception) {
                emit(DataState.Error("$ON_UNKNOWN_ERROR=[${e.message}]"))
            }

        } ?: emit(DataState.Error(ON_LOGIN_PASSWORD_NOT_PROVIDED))

    }.catch { e ->
        emit(DataState.Error("$ON_UNKNOWN_ERROR=[${e.message}]"))
    }
}