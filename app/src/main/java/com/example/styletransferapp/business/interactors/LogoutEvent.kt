package com.example.styletransferapp.business.interactors

import com.example.styletransferapp.business.domain.utils.DataState
import com.example.styletransferapp.representation.auth.login_screen.data.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LogoutEvent
@Inject
constructor(
    private val repo: LoginRepository,
) : BaseUseCase<BaseUseCase.RequestType, BaseUseCase.ResponseType>() {

    companion object {
        const val ON_SUCCESS: String
            = "Successful logout"
    }

    override fun execute(data: RequestType?): Flow<DataState> = flow {
        emit(DataState.Loading)

        repo.logout()

        emit(DataState.Success(ON_SUCCESS))
    }.catch { e ->
        emit(DataState.Error("Unknown error=${e.message}"))
    }
}