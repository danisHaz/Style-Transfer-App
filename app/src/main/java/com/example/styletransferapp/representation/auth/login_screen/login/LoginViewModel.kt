package com.example.styletransferapp.representation.auth.login_screen.login

import com.example.styletransferapp.business.domain.utils.DataState
import com.example.styletransferapp.business.domain.utils.SessionManager
import com.example.styletransferapp.business.interactors.LoginEvent
import com.example.styletransferapp.representation.BaseViewModel
import com.example.styletransferapp.representation.auth.AuthState
import com.example.styletransferapp.business.domain.utils.SessionState
import com.example.styletransferapp.business.interactors.RegistrationEvent
import com.example.styletransferapp.business.services.network.auth.responses.User
import com.example.styletransferapp.utils.Constants

import android.util.Log

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.example.styletransferapp.utils.logUncaughtException

import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class LoginViewModel
@Inject
constructor(
    private val loginEvent: LoginEvent,
    private val registrationEvent: RegistrationEvent,
    private val sessionManager: SessionManager,
) : BaseViewModel<AuthState>() {

    companion object {
        val NAME: String
            = this::class.java.name
        const val ON_INVALID_LOGOUT_ERROR: String
            = "Trying to log out from login screen"
        const val INVALID_REGISTRATION
            = "Registration held from login screen"
        const val UNKNOWN_EXCEPTION: String
            = "Unknown error is thrown"
        const val NO_CODE_PROVIDED_ERROR: String
            = "Null error code in response from repository"
    }

    private var _sessionData: SessionManager.SessionData?
        = sessionManager.data

    val sessionData: SessionManager.SessionData?
        get() = _sessionData
    private var _authState: MutableLiveData<AuthState>
        = MutableLiveData()
    val authState: AuthState
        get() = _authState.value!!

    fun setAuthStateObserver(owner: LifecycleOwner, action: (AuthState) -> Unit) {
        _authState.observe(owner, action)
    }

    override fun changeState(state: AuthState) {
        _authState.value = state
        when (state) {
            is AuthState.OnLogin -> {
                login(state)
            }
            is AuthState.OnLogout -> {
                logout()
            }
            is AuthState.OnRegister -> {
                //-- should never be changed from changeState
                //-- used only when onLogin() is failed to retrieve data
                register(null)
            }
            //-- onLoggedIn, onLoggedOut
            else -> {}
        }
    }

    private fun login(loginState: AuthState.OnLogin) {
        var needsToBeRegistered = false
        var err: String? = null

        loginEvent.execute(loginState.loginPassword)
            .onEach { dataState ->
                when (dataState) {
                    is DataState.Error -> {
                        var reason: String? = null
                        dataState.errorCode?.let { errorCode ->

                            reason = Constants.errorCodes[errorCode]
                            when (errorCode) {
                                //-- register user if not succeeded in logging in
                                19 -> {
                                    needsToBeRegistered = true
                                }

                                else -> {
                                    logUncaughtException(errorCode)
                                    if (reason == null)
                                        reason = UNKNOWN_EXCEPTION
                                }
                            }
                        } ?: Log.e(NAME, NO_CODE_PROVIDED_ERROR)
                        //-- throw CancellationException(reason)
                        err = reason
                    }
                    is DataState.Data<*> -> {
                        sessionManager.handleSessionUpdate(
                            SessionState.LoggedIn(
                                (dataState.data as User).toSessionData()
                            )
                        )
                    }
                    is DataState.Success -> {
                        changeState(AuthState.OnLoggedIn)
                    }
                    else -> {}
                }
            }.launchIn(viewModelScope)
            .invokeOnCompletion {
                when {
                    needsToBeRegistered -> {
                        register(AuthState.OnRegister(loginState.loginPassword))
                    }
                    err == null -> changeState(AuthState.OnLoggedIn)

                    else -> {
                        changeState(AuthState.OnError(err))
                    }
                }
            }
    }

    private fun register(registerState: AuthState.OnRegister?) {
        registerState?.let {
            registrationEvent.execute(registerState.loginPassword)
                .onEach { dataState ->
                    when (dataState) {
                        is DataState.Error -> {
                            dataState.errorCode?.let { errorCode ->
                                changeState(
                                    AuthState.OnError(Constants.errorCodes[errorCode])
                                )
                            } ?: Log.e(NAME, NO_CODE_PROVIDED_ERROR)

                            changeState(AuthState.OnLoggedOut)
                        }
                        is DataState.Success -> {
                            changeState(AuthState.OnRegistered)
                        }
                        else -> {}
                    }
                }.launchIn(viewModelScope)
        } ?: run {
            changeState(AuthState.OnError(INVALID_REGISTRATION))
            Log.e(NAME, INVALID_REGISTRATION)
        }
    }

    private fun logout() {
        Log.e(NAME, ON_INVALID_LOGOUT_ERROR)
    }

}