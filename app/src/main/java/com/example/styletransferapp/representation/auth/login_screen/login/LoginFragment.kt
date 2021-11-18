package com.example.styletransferapp.representation.auth.login_screen.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.viewModels
import com.example.styletransferapp.R
import com.example.styletransferapp.representation.auth.AuthActivity
import com.example.styletransferapp.representation.auth.AuthState
import com.example.styletransferapp.business.domain.utils.auth.LoginPassword
import com.example.styletransferapp.representation.main.BaseFragment

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    companion object {
        val NAME: String = this::class.java.name
    }

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var login: Button
    private var parentActivity: AuthActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username = view.findViewById(R.id.username)
        password = view.findViewById(R.id.password)
        login = view.findViewById(R.id.loginOrRegister)
        parentActivity = (activity as AuthActivity)

        setObservers()

        login.setOnClickListener {
            Log.i(NAME, "login button is clicked")
            val loginPassword = LoginPassword(
                username.text.toString(),
                password.text.toString()
            )

            loginViewModel.changeState(
                AuthState.OnLogin(loginPassword)
            )
        }
    }

    private fun setObservers() {
        loginViewModel.setAuthStateObserver(this) { authState ->
            when (authState) {
                is AuthState.OnLogin -> {
                    parentActivity?.setProgressBar()
                }
                is AuthState.OnRegister -> {
                     parentActivity?.setProgressBar()
                }
                is AuthState.OnLoggedIn -> {
                    parentActivity?.hideProgressbar()
                    // TODO: transition to main nav graph
                }
                // actually this branch should never be succeeded
                is AuthState.OnLogout -> {
                    parentActivity?.setProgressBar()
                    Log.e(NAME, "Succeeded OnLogout branch")
                }
                // actually this branch should never be succeeded?
                is AuthState.OnLoggedOut -> {
                    parentActivity?.hideProgressbar()
                }
                is AuthState.OnRegistered -> {
                    parentActivity?.hideProgressbar()
//                    parentActivity?.setSnackbar()
                }
            }
        }

    }

}