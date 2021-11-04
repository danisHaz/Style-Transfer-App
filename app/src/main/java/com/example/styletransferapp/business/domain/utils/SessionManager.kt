package com.example.styletransferapp.business.domain.utils

import com.example.styletransferapp.business.interactors.BaseUseCase

import android.util.Log

import androidx.lifecycle.MutableLiveData

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SessionManager private constructor() {

    companion object {
        val manager: SessionManager by lazy {
            SessionManager()
        }
        const val CACHE_CLEAN_ERROR = "Clean cache error"
        val NAME = this::class.java.name
        const val INVALID_DATA_STATE = "Invalid data state provided"
    }
    private val coroutineScope: CoroutineScope = CoroutineScope(IO)

    private val currentSessionState: MutableLiveData<SessionState>
        = MutableLiveData<SessionState>()
    private var sessionData: SessionData? = null
    val data: SessionData?
        get() = sessionData

    data class SessionData(val userId: Int) : BaseUseCase.RequestType

    fun handleSessionUpdate(state: SessionState) {
        when(state) {
            is SessionState.LoggedIn -> {
                login(state)
            }
            is SessionState.Logout -> {
                logout(state)
            }
            // actually nothing happens
            is SessionState.LoggedOut -> {  }
        }
    }

    private fun logout(state: SessionState.Logout) {
        // TODO: remove ui observers on currentSessionState
        // TODO: clear all resources as repository, cache(!)...
        sessionData = null
        currentSessionState.value = SessionState.LoggedOut
    }

    private fun login(state: SessionState.LoggedIn) {
        // TODO: set ui observers on currentSessionState
        sessionData = SessionData(state.userData.userId)
        currentSessionState.value = state
    }
}