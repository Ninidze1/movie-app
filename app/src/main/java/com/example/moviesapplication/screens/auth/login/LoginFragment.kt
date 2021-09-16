package com.example.moviesapplication.screens.auth.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moviesapplication.R
import com.example.moviesapplication.base.BaseFragment
import com.example.moviesapplication.databinding.LoginFragmentBinding

class LoginFragment : BaseFragment<LoginFragmentBinding, LoginViewModel>(
    LoginFragmentBinding::inflate,
    LoginViewModel::class.java
) {
    override fun init(inflater: LayoutInflater, container: ViewGroup?) {
        TODO("Not yet implemented")
    }


}