package com.michaelgrigoryan.nite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Fragment Holder
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment

        // Nav controller of Fragment Holder
        val navController = navHostFragment.navController

        // Setting up an action bar to support AndroidX Navigation
        setupActionBarWithNavController(navController)
    }

    override fun onNavigateUp(): Boolean {
        /*
        TODO: When clicking the arrow we need to go back but this doesn't work ( Lines —> 17-24 )
        */
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

        return navController.navigateUp() || super.onNavigateUp()
    }
}