package com.example.styletransferapp.business.domain.utils

import androidx.lifecycle.MutableLiveData
import com.example.styletransferapp.business.interactors.BaseUseCase

class SessionManager private constructor() {

    companion object {
        val manager: SessionManager by lazy {
            SessionManager()
        }
    }
    val currentSessionState: MutableLiveData<Boolean>
        = MutableLiveData<Boolean>()
    private lateinit var sessionData: SessionData
    val data: SessionData
        get() = sessionData

    data class SessionData(val userId: Int) : BaseUseCase.RequestType

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
        // TODO: clear all resources as repository, cache(!)...
        currentSessionState.value = false
    }

    fun login(state: SessionState.Login) {
        // TODO: set ui observers on currentSessionState
        sessionData = SessionData(state.userId)
        currentSessionState.value = true
    }
}