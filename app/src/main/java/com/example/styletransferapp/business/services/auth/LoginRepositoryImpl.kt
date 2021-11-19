package com.example.styletransferapp.business.services.auth

import android.util.Log
import com.example.styletransferapp.business.domain.utils.Result
import com.example.styletransferapp.business.services.network.GenericResponse
import com.example.styletransferapp.business.services.network.auth.AuthService
import com.example.styletransferapp.business.services.network.auth.responses.User
import com.example.styletransferapp.business.domain.utils.auth.LoginPassword
import com.example.styletransferapp.utils.Constants.errorCodes
import com.example.styletransferapp.utils.logUncaughtException
import retrofit2.await
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.log

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

@Singleton
class LoginRepositoryImpl
@Inject
constructor(
    private val authService: AuthService
): LoginRepository {

    companion object {
        val NAME: String
            = this::class.java.name
    }

    override suspend fun register(loginPassword: LoginPassword): Result<Any?> {
        val newUser = User(
            username=loginPassword.username,
            email=loginPassword.username,
            password=loginPassword.password,
            auth_token=null
        )

        val response: GenericResponse<Any?> = authService
            .registerUser(
                newUser.username,
                newUser
            ).await()

        response.errorCode?.let { code ->
            return Result.Error(java.lang.Exception(
                errorCodes[code] ?: logUncaughtException(code)
            ))
        }

        return Result.Success(response.data)
    }

    override suspend fun logout(loginPassword: LoginPassword): Result<Any?> {
        val currentUser = User(
            username=loginPassword.username,
            email=loginPassword.username,
            password=loginPassword.password,
            auth_token=null
        )

        val response: GenericResponse<Any?> = authService
            .logoutUser(
                currentUser.username,
            ).await()

        response.errorCode?.let { code ->
            return Result.Error(java.lang.Exception(
                errorCodes[code] ?: logUncaughtException(code)
            ))
        }

        return Result.Success(response.data)
    }

    override suspend fun login(loginPassword: LoginPassword): Result<User> {
        val newUser = User(
            username=loginPassword.username,
            email=loginPassword.username,
            password=loginPassword.password,
            auth_token=null
        )

        val response: GenericResponse<User> = authService.loginUser(
            loginPassword.username,
            newUser,
        ).await()

        response.errorCode?.let { code ->
            return Result.Error(java.lang.Exception(
                errorCodes[code] ?: logUncaughtException(code)
            ), code)
        }
        return Result.Success(response.data)
    }

}