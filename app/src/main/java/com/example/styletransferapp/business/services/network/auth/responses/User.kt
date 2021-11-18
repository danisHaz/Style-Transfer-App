package com.example.styletransferapp.business.services.network.auth.responses

import com.example.styletransferapp.business.domain.utils.SessionManager
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("username")
    val username: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password_hashed")
    val password: String,
    @SerializedName("auth_token")
    val auth_token: String?
) {
    @Throws(java.lang.NullPointerException::class)
    fun toSessionData() = SessionManager.SessionData(
        username = username,
        email = email,
        password = password,
        auth_token = auth_token!!,
    )
}
