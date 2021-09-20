package com.example.moviesapplication.screens.splash

import android.animation.Animator
import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.moviesapplication.R
import com.example.moviesapplication.base.BaseFragment
import com.example.moviesapplication.databinding.SplashScreenFragmentBinding
import com.example.moviesapplication.extensions.setGone
import dagger.hilt.android.AndroidEntryPoint


@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenFragment : BaseFragment<SplashScreenFragmentBinding, SplashScreenViewModel>(
    SplashScreenFragmentBinding::inflate,
    SplashScreenViewModel::class.java
) {

    override fun init(inflater: LayoutInflater, container: ViewGroup?) {
        setUpAnimation()
    }

    private fun setUpAnimation() {
        Log.d("taglag", "animation")
        binding.lottie.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                binding.lottie.setGone()
                findNavController().navigate(R.id.action_splashScreenFragment_to_navigation_dashboard)

            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }

        })
    }

    private fun invalidateSession() {

    }



}