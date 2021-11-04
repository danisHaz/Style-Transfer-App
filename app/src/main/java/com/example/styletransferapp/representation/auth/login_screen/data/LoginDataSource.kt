package com.example.styletransferapp.representation.auth.login_screen.data

import com.example.styletransferapp.business.domain.utils.Result
import com.example.styletransferapp.business.domain.utils.SessionManager
import com.example.styletransferapp.representation.auth.login_screen.data.model.LoginPassword

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    suspend fun login(loginPassword: LoginPassword): Result<SessionManager.SessionData> {
        // TODO: implement logic for retrofit queries

        return Result.Error(java.lang.UnsupportedOperationException())
    }

    suspend fun logout() {
        // TODO: retrofit query for
        //  cleaning data about user session
    }
}