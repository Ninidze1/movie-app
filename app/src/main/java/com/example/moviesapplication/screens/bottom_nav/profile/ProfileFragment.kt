package com.example.moviesapplication.screens.bottom_nav.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.moviesapplication.R
import com.example.moviesapplication.base.BaseFragment
import com.example.moviesapplication.databinding.ProfileFragmentBinding
import com.example.moviesapplication.repository.firebase.FirebaseRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment<ProfileFragmentBinding, ProfileViewModel>(
    ProfileFragmentBinding::inflate,
    ProfileViewModel::class.java
) {
    @Inject
    lateinit var auth: FirebaseRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (auth.getUser()?.uid == null)
            findNavController().navigate(R.id.main_auth)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun init(inflater: LayoutInflater, container: ViewGroup?) {
        listeners()
    }

    private fun listeners() {
        binding.signOut.setOnClickListener {
            auth.signOut()
            findNavController().navigate(R.id.main_auth)
        }
    }


}