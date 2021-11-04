package com.example.styletransferapp.representation.auth.login_screen.login

import com.example.styletransferapp.business.domain.utils.DataState
import com.example.styletransferapp.business.domain.utils.SessionManager
import com.example.styletransferapp.business.interactors.LoginEvent
import com.example.styletransferapp.business.interactors.LogoutEvent
import com.example.styletransferapp.representation.BaseViewModel
import com.example.styletransferapp.representation.auth.AuthState

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope

import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay

import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@ViewModelScoped
class LoginViewModel
@Inject
constructor(
    private val loginEvent: LoginEvent,
    private val logoutEvent: LogoutEvent,
    private val sessionManager: SessionManager,
) : BaseViewModel<AuthState>() {

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
//                login(state)
                CoroutineScope(IO).launch {
                    delay(1000L)
                    _authState.value = AuthState.OnLoggedIn
                }
            }
            is AuthState.OnLogout -> {
//                logout()
                CoroutineScope(IO).launch {
                    delay(1000L)
                    _authState.value = AuthState.OnLoggedOut
                }
            }
            is AuthState.OnRegister -> {
                //-- should never be changed from changeState
                //-- used only when onlogin is failed to retrieve data
            }
            is AuthState.OnLoggedOut -> {
                //-- todo: clear data
            }
            is AuthState.OnLoggedIn -> {
                //-- ok then
            }
        }
    }

    private fun login(loginState: AuthState.OnLogin) {
        loginEvent
            .execute(loginState.loginPassword)
            .onEach { dataState ->
                when (dataState) {
                    is DataState.Success -> { }
                    is DataState.Error -> { }
                    is DataState.Loading -> { }
                    is DataState.Data<*> -> { }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun logout() {
        logoutEvent
            .execute()
            .onEach { dataState ->
                when (dataState) {
                    is DataState.Success -> { }
                    is DataState.Error -> { }
                    is DataState.Loading -> { }
                    is DataState.Data<*> -> { }
                }
            }
            .launchIn(viewModelScope)
    }

}