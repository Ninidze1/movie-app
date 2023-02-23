package com.example.moviesapplication.ui.auth

import androidx.navigation.fragment.NavHostFragment
import com.example.common.base.BaseFragment
import com.example.moviesapplication.R
import com.example.moviesapplication.databinding.AuthFragmentBinding

class AuthFragment : BaseFragment<AuthFragmentBinding>(AuthFragmentBinding::inflate) {

    override fun init() {
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