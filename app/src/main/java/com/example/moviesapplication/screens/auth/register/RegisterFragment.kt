package com.example.moviesapplication.screens.auth.register

import android.util.Log.d
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.example.moviesapplication.R
import com.example.moviesapplication.base.BaseFragment
import com.example.moviesapplication.extensions.resetField
import com.example.moviesapplication.databinding.RegisterFragmentBinding
import com.example.moviesapplication.extensions.isValidEmail
import com.example.moviesapplication.extensions.setErrorField
import com.example.moviesapplication.extensions.showToast

class RegisterFragment : BaseFragment<RegisterFragmentBinding, RegisterViewModel>(
    RegisterFragmentBinding::inflate,
    RegisterViewModel::class.java
) {
    override fun init(inflater: LayoutInflater, container: ViewGroup?) {
        listeners()
    }

    private fun listeners() {
        resetFields()
        binding.backToLoginBtn.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        binding.registerBtn.setOnClickListener {
            checkFields()
        }
    }

    private fun checkFields() {
        if (!binding.emailRegEt.text.toString().isValidEmail())
            binding.emailRegEt.setErrorField("Invalid Email")

        if (binding.passwordRegEt.text?.length!! < 8)
            binding.passwordRegEt.setErrorField("Too short password, min 8 character")

        if (binding.passwordRegEt.text.toString() != binding.passwordConfRegEt.text.toString()) {
            binding.passwordRegEt.setErrorField()
            binding.passwordConfRegEt.setErrorField("Passwords don't match")
        }
    }

    private fun resetFields() {
        binding.emailRegEt.resetField()
        binding.passwordConfRegEt.resetField()
        binding.passwordRegEt.resetField()
    }

}