package com.example.moviesapplication

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.moviesapplication.databinding.ActivityMainBinding
import com.example.moviesapplication.extensions.setGone
import com.example.moviesapplication.extensions.show
import com.ismaeldivita.chipnavigation.ChipNavigationBar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavHostFragment

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

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_dashboard -> {
                    chipNavigation.setItemSelected(R.id.navigation_dashboard)
                }
                R.id.navigation_favourites -> {
                    chipNavigation.setItemSelected(R.id.navigation_favourites)
                }
                R.id.navigation_profile -> {
                    chipNavigation.setItemSelected(R.id.navigation_profile)
                }
                R.id.main_auth -> {
                    handleBackPressed(destination)
                }
            }
            hideIfAuth(destination, chipNavigation)
        }

    }

    private fun handleBackPressed(destination: NavDestination) {
        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                when (destination.id) {
                    R.id.main_auth -> {
                        navController = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                        navController.navController.navigate(R.id.action_global_navigation_dashboard)
                    }
                }
            }
        })
    }

    private fun hideIfAuth(destination: NavDestination, navBar: ChipNavigationBar) {

        if (destination.id == R.id.main_auth)
            navBar.setGone()
        else {
            navBar.show()
        }
    }
}