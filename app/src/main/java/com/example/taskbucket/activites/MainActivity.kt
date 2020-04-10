package com.example.taskbucket.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.taskbucket.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    lateinit var mBottomNav: BottomNavigationView
    lateinit var mDrawerLayout: DrawerLayout
    lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mBottomNav = findViewById(R.id.bottom_nav)
        mDrawerLayout = findViewById(R.id.drawer_layout)
        toolbar = findViewById(R.id.toolbar)
        val controller = findNavController(R.id.nav_host_fragment)
        mBottomNav.setupWithNavController(controller)
        val appBarConfiguration = AppBarConfiguration(controller.graph, mDrawerLayout)
        setSupportActionBar(toolbar)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //supportActionBar!!.setDisplayShowHomeEnabled(true)

        //setActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bucket_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
