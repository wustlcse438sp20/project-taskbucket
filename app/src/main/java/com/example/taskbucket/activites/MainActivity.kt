package com.example.taskbucket.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.taskbucket.R
import com.google.android.material.bottomnavigation.BottomNavigationView

lateinit var mBottomNav: BottomNavigationView
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mBottomNav = findViewById(
            R.id.bottom_nav
        )
        val controller = findNavController(R.id.nav_host_fragment)
        mBottomNav.setupWithNavController(controller)
    }
}
