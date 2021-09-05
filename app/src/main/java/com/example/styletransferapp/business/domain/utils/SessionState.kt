package com.example.styletransferapp.business.domain.utils

import com.example.styletransferapp.business.interactors.ClearCacheAndSessionEvent

sealed class SessionState {
    data class Login(val userId: Int) : SessionState()
    data class Logout(val clearCacheAndSessionEvent: ClearCacheAndSessionEvent) : SessionState()
}