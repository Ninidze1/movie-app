package com.example.moviesapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.moviesapplication.databinding.ActivityMainBinding
import com.ismaeldivita.chipnavigation.ChipNavigationBar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNav()

    }
    private fun initBottomNav() {

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val navView = binding.navView
        val chipNavigation: ChipNavigationBar = binding.bottomNavBar
        chipNavigation.setItemSelected(R.id.navigation_dashboard)


        chipNavigation.setOnItemSelectedListener { itemId ->
            navView.selectedItemId = itemId
        }
        NavigationUI.setupWithNavController(navView, navController)
    }
}