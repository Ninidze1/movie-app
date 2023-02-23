package com.example.moviesapplication.ui.auth.login

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.common.base.BaseFragment
import com.example.common.extensions.resetField
import com.example.common.extensions.setErrorField
import com.example.common.extensions.showToast
import com.example.moviesapplication.R
import com.example.moviesapplication.databinding.LoginFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginFragmentBinding>(
    LoginFragmentBinding::inflate
) {

    private val viewModel by viewModels<LoginViewModel>()

    override fun init() {
        listeners()
        observers()
    }

    private fun listeners() {
        binding.registerBtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.resetBtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_resetFragment)
        }
        binding.loginBtn.setOnClickListener {
            checkInputs()
        }
        binding.emailEt.resetField()
        binding.passwordEt.resetField()
    }

    private fun checkInputs() {
        val mail = binding.emailEt.text.toString().trim()
        val password = binding.passwordEt.text.toString().trim()
        if (mail.isNotBlank() && password.isNotEmpty())
            signIn(mail, password)
        else
            requireActivity().showToast("Fill the fields")
    }

    private fun signIn(mail: String, password: String) {
        viewModel.login(mail, password)

    }

    private fun observers() {
        viewModel.loginStatus.observe(viewLifecycleOwner) { status ->
            if (status) {
                val navController =
                    requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                navController.navController.navigate(R.id.action_global_navigation_dashboard)
            } else {
                binding.emailEt.setErrorField()
                binding.passwordEt.setErrorField("Invalid user")
            }
        }
    }


}