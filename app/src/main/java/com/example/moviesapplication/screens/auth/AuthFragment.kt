package com.example.moviesapplication.screens.auth

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.example.moviesapplication.R
import com.example.moviesapplication.base.BaseFragment
import com.example.moviesapplication.databinding.AuthFragmentBinding

class AuthFragment : BaseFragment<AuthFragmentBinding, AuthViewModel>(
    AuthFragmentBinding::inflate,
    AuthViewModel::class.java
) {
    override fun init(inflater: LayoutInflater, container: ViewGroup?) {
        backButton()
    }

    private fun backButton() {
        binding.authBackButton.setOnClickListener {
            val navController =
                requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navController.navController.navigate(R.id.action_global_navigation_dashboard)
        }
    }


}