package com.example.styletransferapp.business.domain.utils

sealed class SessionState {
    data class Login(val userId: Int) : SessionState()
    data class Logout(val userId: Int) : SessionState()
}