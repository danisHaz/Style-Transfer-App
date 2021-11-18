package com.example.styletransferapp.representation.auth

import com.example.styletransferapp.R
import com.example.styletransferapp.representation.BaseActivity
import com.google.android.material.appbar.MaterialToolbar

import android.os.Bundle
import android.widget.ProgressBar

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : BaseActivity() {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var navController: NavController
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val navHostFragment: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.auth_fragment_container) as NavHostFragment

        navController = navHostFragment.navController
        progressBar = findViewById(R.id.auth_progress_bar)
        setToolbar()
    }

    private fun setToolbar() {
        toolbar = findViewById(R.id.auth_toolbar)
        setSupportActionBar(toolbar)
        //-- TODO: set function to support back buttons with navigation
    }

    override fun setProgressBar() {
        progressBar.visibility = ProgressBar.VISIBLE
    }

    override fun hideProgressbar() {
        progressBar.visibility = ProgressBar.GONE
    }
}