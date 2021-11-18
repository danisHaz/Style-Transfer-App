package com.example.styletransferapp.business.services.auth

import com.example.styletransferapp.business.domain.utils.Result
import com.example.styletransferapp.business.services.network.auth.responses.User
import com.example.styletransferapp.business.domain.utils.auth.LoginPassword

interface LoginRepository {

    suspend fun logout(loginPassword: LoginPassword): Result<Any?>
    suspend fun register(loginPassword: LoginPassword): Result<Any?>
    suspend fun login(loginPassword: LoginPassword): Result<User>
}