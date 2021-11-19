package com.example.styletransferapp.business.domain.utils

import com.example.styletransferapp.business.interactors.BaseUseCase
import com.example.styletransferapp.business.services.network.auth.responses.User

import androidx.lifecycle.MutableLiveData

class SessionManager private constructor() {

    companion object {
        val manager: SessionManager by lazy {
            SessionManager()
        }
    }

    private val currentSessionState: MutableLiveData<SessionState>
        = MutableLiveData<SessionState>()
    private var sessionData: SessionData? = null
    val data: SessionData?
        get() = sessionData

    data class SessionData(
        val userId: Int = 10,
        val username: String,
        val email: String,
        val password: String,
        val auth_token: String,
    ): BaseUseCase.RequestType {

        fun toUserInst() = User(
            username = username,
            email = username,
            password = password,
            auth_token = auth_token,
        )
    }

    fun handleSessionUpdate(state: SessionState) {
        when(state) {
            is SessionState.LoggedIn -> {
                login(state)
            }
            is SessionState.Logout -> {
                logout()
            }
            // actually nothing happens
            is SessionState.LoggedOut -> {  }
        }
    }

    private fun logout() {
        sessionData = null
        currentSessionState.value = SessionState.LoggedOut
    }

    //-- mustn't be executed in coroutine
    private fun login(state: SessionState.LoggedIn) {
        // TODO: set ui observers on currentSessionState
        sessionData = state.userData
        currentSessionState.value = state
    }
}