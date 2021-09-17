package com.example.moviesapplication.screens.auth.login

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.moviesapplication.R
import com.example.moviesapplication.base.BaseFragment
import com.example.moviesapplication.databinding.LoginFragmentBinding
import com.example.moviesapplication.extensions.resetField
import com.example.moviesapplication.extensions.setErrorField
import com.example.moviesapplication.extensions.showToast
import com.example.moviesapplication.repository.firebase.FirebaseRepositoryImpl
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginFragmentBinding, LoginViewModel>(
    LoginFragmentBinding::inflate,
    LoginViewModel::class.java
) {
    @Inject
    lateinit var auth: FirebaseRepositoryImpl

    override fun init(inflater: LayoutInflater, container: ViewGroup?) {
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
        viewModel.loginStatus.observe(viewLifecycleOwner, { status ->
            if (status) {
                val navController =
                    requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                navController.navController.navigate(R.id.action_global_navigation_dashboard)
            } else {
                binding.emailEt.setErrorField()
                binding.passwordEt.setErrorField("Invalid user")
            }
        })
    }


}