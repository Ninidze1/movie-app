package com.example.moviesapplication.ui.auth.register

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.common.base.BaseFragment
import com.example.common.extensions.isValidEmail
import com.example.common.extensions.resetField
import com.example.common.extensions.setErrorField
import com.example.common.extensions.showToast
import com.example.moviesapplication.R
import com.example.moviesapplication.databinding.RegisterFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<RegisterFragmentBinding>(
    RegisterFragmentBinding::inflate
) {
    private val viewModel by viewModels<RegisterViewModel>()

    override fun init() {
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
        viewModel.registerStatus.observe(viewLifecycleOwner) { status ->
            if (status) {
                val navController =
                    requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                navController.navController.navigate(R.id.action_global_navigation_dashboard)
            } else {
                requireActivity().showToast("User already exists")
            }
        }
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

        if (password.length < 6)
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