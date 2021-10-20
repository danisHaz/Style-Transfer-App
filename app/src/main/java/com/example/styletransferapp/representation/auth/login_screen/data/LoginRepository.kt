package com.example.styletransferapp.representation.auth.login_screen.data

import com.example.styletransferapp.business.domain.utils.SessionManager
import com.example.styletransferapp.representation.auth.login_screen.data.model.LoginPassword

interface LoginRepository {

    suspend fun logout()

    suspend fun login(loginPassword: LoginPassword): Result<SessionManager.SessionData>
}