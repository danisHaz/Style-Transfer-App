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
        const val DEFAULT_USER_ID = -1
        const val CACHE_CLEAR_ERROR = "Clear cache error"
        val NAME = this::class.java.name
        const val INVALID_DATA_STATE = "Invalid data state provided"
    }
    private val coroutineScope: CoroutineScope = CoroutineScope(IO)

    val currentSessionState: MutableLiveData<SessionState>
        = MutableLiveData<SessionState>()
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
        coroutineScope.launch {
            sessionData = SessionData(DEFAULT_USER_ID)
            state.clearCacheAndSessionEvent.execute(null).collect { dataState ->
                when (dataState) {
                    is DataState.Loading -> {
                        //-- Loading...
                    }
                    is DataState.Error -> {
                        //-- tell observers to notify user about error
                        Log.e(NAME, dataState.message ?: CACHE_CLEAR_ERROR)
                    }
                    is DataState.Success -> {
                        //-- cool nice -> have fun guys
                    }
                    else -> {
                        Log.w(NAME, INVALID_DATA_STATE)
                    }
                }
            }
            withContext(Main) {
                currentSessionState.value = state
            }
        }
    }

    fun login(state: SessionState.Login) {
        // TODO: set ui observers on currentSessionState
        sessionData = SessionData(state.userId)
        currentSessionState.value = state
    }
}