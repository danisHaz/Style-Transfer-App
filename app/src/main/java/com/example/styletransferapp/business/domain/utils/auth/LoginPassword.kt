package com.example.styletransferapp.business.domain.utils.auth

import android.util.Patterns
import com.example.styletransferapp.business.interactors.BaseUseCase

class LoginPassword(
    val username: String,
    val password: String,
) : BaseUseCase.RequestType {
    var isDataValid: Boolean = true

    init {
        if (!isPasswordValid(password) || !isUserNameValid(username)) {
            isDataValid = false
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

}