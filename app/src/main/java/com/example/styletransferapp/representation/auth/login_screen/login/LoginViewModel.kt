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
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.styletransferapp.business.interactors.EventResult
import com.example.styletransferapp.business.interactors.GenericData
import com.example.styletransferapp.utils.logUncaughtException

import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

import javax.inject.Inject

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

        //-- event related info
        const val needsToBeRegistered: String
            = "needs_to_be_registered"

        const val errorReason: String
            = "error_reason"
    }

    private val _authState: MutableLiveData<AuthState> by lazy {
        MutableLiveData(AuthState.OnLoggedOut)
    }

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
        val eResult = EventResult<GenericData<*>>()

        loginEvent.execute(loginState.loginPassword).onEach { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    dataState.errorCode?.let { errorCode ->
                        eResult.addData(errorReason, Constants.errorReason[errorCode])
                        when (errorCode) {
                            //-- register user if not succeeded in logging in
                            19 -> eResult.addData(needsToBeRegistered, true)
                            else -> {
                                logUncaughtException(errorCode)
                            }
                        }
                    } ?: Log.e(NAME, NO_CODE_PROVIDED_ERROR)
                    if (eResult.getDataByKey<String>(errorReason) == null)
                        eResult.addData(errorReason, UNKNOWN_EXCEPTION)

                    //-- throw CancellationException(reason)
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
                eResult.getDataByKey<String>(needsToBeRegistered) != null ->
                    register(AuthState.OnRegister(loginState.loginPassword))
                eResult.getDataByKey<String>(errorReason) == null ->
                    changeState(AuthState.OnLoggedIn)

                else ->
                    changeState(
                        AuthState.OnError(eResult.getDataByKey(errorReason))
                    )
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
                                    AuthState.OnError(Constants.errorReason[errorCode])
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