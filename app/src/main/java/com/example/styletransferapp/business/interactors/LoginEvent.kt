package com.example.styletransferapp.business.interactors

import com.example.styletransferapp.business.domain.utils.DataState
import com.example.styletransferapp.business.domain.utils.SessionManager
import com.example.styletransferapp.business.domain.utils.SessionState
import com.example.styletransferapp.representation.auth.login_screen.data.LoginRepository
import com.example.styletransferapp.business.domain.utils.Result
import com.example.styletransferapp.representation.auth.login_screen.data.model.LoginPassword
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class LoginEvent
@Inject
constructor(
    private val repo: LoginRepository,
    private val sessionManager: SessionManager,
) : BaseUseCase<LoginPassword, DataState.Data<*>>() {
    companion object {
        const val ON_SUCCESS: String = "Successful log in"
        const val ON_LOGIN_PASSWORD_NOT_PROVIDED
            = "Login and password not provided"
        const val ON_BAD_LOGIN_ERROR
            = "Incorrect login or password provided"
        const val ON_UNKNOWN_ERROR = "Undetermined error"
    }

    override fun execute(data: LoginPassword?): Flow<DataState> = flow {
        data?.let {
            emit(DataState.Loading)
            try {
                val result = repo.login(it)
                lateinit var userData: DataState.Data<SessionManager.SessionData>

                when (result) {
                    is Result.Success -> { userData = DataState.Data(result.data!!) }
                    is Result.Error -> {
                        emit(DataState.Error(result.exception.message))
                        return@flow
                    }
                }

                sessionManager.handleSessionUpdate(SessionState.LoggedIn(userData.data))
                emit(userData)
            } catch (e: Exception) {
                emit(DataState.Error("$ON_UNKNOWN_ERROR=[${e.message}]"))
            }

            emit(DataState.Success(ON_SUCCESS))
        } ?: emit(DataState.Error(ON_LOGIN_PASSWORD_NOT_PROVIDED))

    }.catch { e ->
        emit(DataState.Error("$ON_UNKNOWN_ERROR=[${e.message}]"))
    }
}