package com.example.moviesapplication.screens.auth.reset

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.example.moviesapplication.R
import com.example.moviesapplication.base.BaseFragment
import com.example.moviesapplication.databinding.ResetFragmentBinding
import com.example.moviesapplication.extensions.resetField
import com.example.moviesapplication.extensions.setErrorField
import com.example.moviesapplication.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResetFragment: BaseFragment<ResetFragmentBinding, ResetViewModel>(
    ResetFragmentBinding::inflate,
    ResetViewModel::class.java
) {
    override fun init(inflater: LayoutInflater, container: ViewGroup?) {
        listeners()
        observers()
    }

    private fun listeners() {
        binding.emailEtReset.resetField()

        binding.resetBtn.setOnClickListener {
            binding.emailEtReset.setErrorField("Invalid Email")
            val email = binding.emailEtReset.text.toString().trim()
            viewModel.resetPassword(email)
        }
    }

    private fun observers() {
        viewModel.resetStatus.observe(viewLifecycleOwner, { status ->
            if (status) {
                requireContext().showToast("Check email")
                val navController =
                    requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                navController.navController.navigate(R.id.action_resetFragment_to_loginFragment)
            } else {
                binding.emailEtReset.setErrorField()
            }
        })
    }

}