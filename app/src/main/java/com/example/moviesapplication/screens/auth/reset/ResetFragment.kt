package com.example.moviesapplication.screens.auth.reset

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moviesapplication.R
import com.example.moviesapplication.base.BaseFragment
import com.example.moviesapplication.databinding.ResetFragmentBinding
import com.example.moviesapplication.extensions.isValidEmail
import com.example.moviesapplication.extensions.resetField
import com.example.moviesapplication.extensions.setErrorField

class ResetFragment : BaseFragment<ResetFragmentBinding, ResetViewModel>(
    ResetFragmentBinding::inflate,
    ResetViewModel::class.java
) {
    override fun init(inflater: LayoutInflater, container: ViewGroup?) {
        listeners()
    }

    private fun listeners() {
        binding.emailEtReset.resetField()

        binding.resetBtn.setOnClickListener {
            binding.emailEtReset.setErrorField("Invalid Email")
        }
    }

}