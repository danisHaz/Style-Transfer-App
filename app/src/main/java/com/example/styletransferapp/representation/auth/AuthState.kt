package com.example.styletransferapp.representation.auth

import com.example.styletransferapp.representation.auth.login_screen.data.model.LoginPassword
import com.example.styletransferapp.representation.utils.BaseState

sealed class AuthState : BaseState {
    data class OnRegister(val loginPassword: LoginPassword) : AuthState()
    data class OnLogin(val loginPassword: LoginPassword) : AuthState()
    object OnLoggedIn : AuthState()
    object OnLogout : AuthState()
    object OnLoggedOut : AuthState()
}
