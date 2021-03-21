package com.michaelgrigoryan.nite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Fragment Holder
        val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment

        // Nav controller of Fragment Holder
        val navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)

        // Setting up an action bar to support AndroidX Navigation
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    /*
     * When using setupActionBarWithNavController(),
     * you need to override onSupportNavigateUp()
     * to trigger the Up icon in the Action Bar
     * as per the documentation at:
     * https://developer.android.com/guide/navigation/navigation-ui#action_bar
     */
    override fun onSupportNavigateUp(): Boolean {

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}