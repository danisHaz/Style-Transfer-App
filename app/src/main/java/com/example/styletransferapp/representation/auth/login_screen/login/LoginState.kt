package com.example.styletransferapp.representation.auth.login_screen.login

/**
 * Data validation state of the login form.
 */
data class LoginState(
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false
)