package com.example.styletransferapp.representation.auth

import com.example.styletransferapp.business.domain.utils.auth.LoginPassword
import com.example.styletransferapp.representation.utils.BaseState

sealed class AuthState : BaseState {
    data class OnRegister(val loginPassword: LoginPassword) : AuthState()
    object OnRegistered : AuthState()
    data class OnLogin(val loginPassword: LoginPassword) : AuthState()
    object OnLoggedIn : AuthState()
    object OnLogout : AuthState()
    object OnLoggedOut : AuthState()
    data class OnError(val message: String?) : AuthState()
}
