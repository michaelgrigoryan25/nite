package com.michaelgrigoryan.nite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActionBarWithNavController(
            findNavController(R.id.nav_host_fragment_container)
        )
    }

    override fun onNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment_container).navigateUp() || super.onNavigateUp()
    }
}