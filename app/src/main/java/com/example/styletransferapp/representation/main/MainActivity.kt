package com.example.styletransferapp.representation.main

import com.example.styletransferapp.R
import com.example.styletransferapp.representation.BaseActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toolbar

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.card.MaterialCardView
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var navController: NavController
    private lateinit var progressBar: ProgressBar
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.main_fragment_container) as NavHostFragment

        navController = navHostFragment.navController
        progressBar = findViewById(R.id.progress_bar)
        tabLayout = findViewById(R.id.main_nav_tabs)
        setToolbar()
        setTabConfigs()
    }

    private fun setToolbar() {
        toolbar = findViewById(R.id.main_toolbar)
        setSupportActionBar(toolbar)
        //-- TODO: set function to support back buttons with navigation
    }

    private fun setTabConfigs() {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun setProgressBar() {
        progressBar.visibility = ProgressBar.VISIBLE
    }

    override fun hideProgressbar() {
        progressBar.visibility = ProgressBar.INVISIBLE
    }
}