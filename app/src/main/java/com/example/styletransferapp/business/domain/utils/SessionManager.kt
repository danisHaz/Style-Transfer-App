package com.example.styletransferapp.business.domain.utils

import androidx.lifecycle.MutableLiveData

class SessionManager private constructor() {

    companion object {
        val manager: SessionManager by lazy {
            SessionManager()
        }
    }
    val currentSessionState: MutableLiveData<Boolean>
        = MutableLiveData<Boolean>()
    lateinit var sessionData: SessionData

    data class SessionData(val userId: Int)

    fun handleSessionUpdate(state: SessionState) {
        when(state) {
            is SessionState.Login -> {
                login(state)
            }
            is SessionState.Logout -> {
                logout(state)
            }
        }
    }

    fun logout(state: SessionState.Logout) {
        // TODO: remove ui observers on currentSessionState
        currentSessionState.value = false
    }

    fun login(state: SessionState.Login) {
        // TODO: set ui observers on currentSessionState
        sessionData = SessionData(state.userId)
        currentSessionState.value = true
    }
}