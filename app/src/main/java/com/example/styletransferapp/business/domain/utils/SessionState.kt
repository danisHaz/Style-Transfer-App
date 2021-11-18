package com.example.styletransferapp.business.domain.utils

import com.example.styletransferapp.business.interactors.ClearCacheAndSessionEvent

sealed class SessionState {
    data class LoggedIn(val userData: SessionManager.SessionData) : SessionState()
    object LoggedOut : SessionState()
    object Logout : SessionState()

    override fun toString(): String
        = when (this) {
            is LoggedIn -> "User is logged in: username=[${userData.username}]"
            is LoggedOut -> "User is null"
            is Logout -> "User is logging out"
        }
}