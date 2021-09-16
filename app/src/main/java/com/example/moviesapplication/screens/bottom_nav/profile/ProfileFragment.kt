package com.example.moviesapplication.screens.bottom_nav.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moviesapplication.R
import com.example.moviesapplication.base.BaseFragment
import com.example.moviesapplication.databinding.ProfileFragmentBinding

class ProfileFragment : BaseFragment<ProfileFragmentBinding, ProfileViewModel>(
    ProfileFragmentBinding::inflate,
    ProfileViewModel::class.java
) {
    override fun init(inflater: LayoutInflater, container: ViewGroup?) {
        TODO("Not yet implemented")
    }


}