package com.example.moviesapplication.screens.auth.register

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.moviesapplication.R
import com.example.moviesapplication.base.BaseFragment
import com.example.moviesapplication.databinding.RegisterFragmentBinding
import com.example.moviesapplication.extensions.isValidEmail
import com.example.moviesapplication.extensions.resetField
import com.example.moviesapplication.extensions.setErrorField
import com.example.moviesapplication.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<RegisterFragmentBinding, RegisterViewModel>(
    RegisterFragmentBinding::inflate,
    RegisterViewModel::class.java
) {
    override fun init(inflater: LayoutInflater, container: ViewGroup?) {
        listeners()
        observers()
    }

    private fun listeners() {
        resetFields()
        binding.backToLoginBtn.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        binding.registerBtn.setOnClickListener {
            if (checkFields() == 4) {
                val email = binding.emailRegEt.text.toString().trim()
                val password = binding.passwordRegEt.text.toString().trim()
                viewModel.register(email, password)
            }
        }
    }

    private fun observers() {
        viewModel.registerStatus.observe(viewLifecycleOwner, { status ->
            if (status) {
                val navController =
                    requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                navController.navController.navigate(R.id.action_global_navigation_dashboard)
            } else {
                requireActivity().showToast("User already exists")
            }
        })
    }

    private fun checkFields(): Int {
        var status = 0

        val name = binding.nameRegEt.text.toString().trim()
        val mail = binding.emailRegEt.text.toString().trim()
        val password = binding.passwordRegEt.text.toString().trim()
        val repPassword = binding.passwordConfRegEt.text.toString().trim()

        if (name.isEmpty())
            binding.nameRegEt.setErrorField("Enter name")
        else status++

        if (!mail.isValidEmail())
            binding.emailRegEt.setErrorField("Invalid Email")
        else
            status++

        if (password.length < 8)
            binding.passwordRegEt.setErrorField("Too short password, min 8 character")
        else
            status++

        if (password != repPassword) {
            binding.passwordRegEt.setErrorField()
            binding.passwordConfRegEt.setErrorField("Passwords don't match")
        } else
            status++

        return status
    }

    private fun resetFields() {
        binding.emailRegEt.resetField()
        binding.passwordConfRegEt.resetField()
        binding.passwordRegEt.resetField()
    }

}