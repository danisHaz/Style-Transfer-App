package com.example.styletransferapp.representation.auth.login_screen.data

import android.util.Log
import com.example.styletransferapp.business.domain.utils.SessionManager
import com.example.styletransferapp.representation.auth.login_screen.data.model.LoginPassword
import javax.inject.Inject

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepositoryImpl : LoginRepository {

    @Inject lateinit var dataSource: LoginDataSource

    companion object {
        val NAME = this::class.java.name
        const val LOGIN_RESULT_ERROR = "Error when trying to log in"
    }

    override suspend fun logout() {
        dataSource.logout()
    }

    override suspend fun login(loginPassword: LoginPassword): Result<SessionManager.SessionData> {
        val result = dataSource.login(loginPassword)

        if (result is Result.Error)
            Log.e(NAME, "$LOGIN_RESULT_ERROR=[${result.exception}]")

        return result
    }

}