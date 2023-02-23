package com.example.moviesapplication.ui.auth.reset

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.common.base.BaseFragment
import com.example.common.extensions.resetField
import com.example.common.extensions.setErrorField
import com.example.common.extensions.showToast
import com.example.moviesapplication.R
import com.example.moviesapplication.databinding.ResetFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResetFragment: BaseFragment<ResetFragmentBinding>(
    ResetFragmentBinding::inflate
) {
    private val viewModel by viewModels<ResetViewModel>()

    override fun init() {
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
        viewModel.resetStatus.observe(viewLifecycleOwner) { status ->
            if (status) {
                requireContext().showToast("Check email")
                val navController =
                    requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                navController.navController.navigate(R.id.action_resetFragment_to_loginFragment)
            } else {
                binding.emailEtReset.setErrorField()
            }
        }
    }

}