package com.example.styletransferapp.representation.main

import com.example.styletransferapp.R
import com.example.styletransferapp.representation.BaseActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toolbar

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.card.MaterialCardView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var toolbar: MaterialToolbar
    private lateinit var navController: NavController
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.main_fragment_container) as NavHostFragment

        navController = navHostFragment.navController
        progressBar = findViewById(R.id.progress_bar)
        setToolbar()
        setBottomNavigation()
    }

    private fun setToolbar() {
        toolbar = findViewById(R.id.main_toolbar)
        setSupportActionBar(toolbar)
        //-- TODO: set function to support back buttons with navigation
    }

    private fun setBottomNavigation() {
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setupWithNavController(navController)
        //-- TODO: probably need to add setOnNavigationItemSelectedListener for actual navigation
    }

    override fun setProgressBar() {
        progressBar.visibility = ProgressBar.VISIBLE
    }

    override fun hideProgressbar() {
        progressBar.visibility = ProgressBar.INVISIBLE
    }
}