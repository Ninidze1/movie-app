package com.example.moviesapplication.screens.auth.login

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.moviesapplication.R
import com.example.moviesapplication.base.BaseFragment
import com.example.moviesapplication.databinding.LoginFragmentBinding
import com.example.moviesapplication.extensions.resetField

class LoginFragment : BaseFragment<LoginFragmentBinding, LoginViewModel>(
    LoginFragmentBinding::inflate,
    LoginViewModel::class.java
) {
    override fun init(inflater: LayoutInflater, container: ViewGroup?) {
        listeners()
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

    }


}