package com.example.moviesapplication.ui.splash

import android.animation.Animator
import android.annotation.SuppressLint
import androidx.navigation.fragment.findNavController
import com.example.common.base.BaseFragment
import com.example.common.extensions.setGone
import com.example.moviesapplication.R
import com.example.moviesapplication.databinding.SplashScreenFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenFragment : BaseFragment<SplashScreenFragmentBinding>(
    SplashScreenFragmentBinding::inflate
) {

    override fun init() {
        setUpAnimation()
    }

    private fun setUpAnimation() {
        binding.lottie.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
            }

            override fun onAnimationEnd(animation: Animator) {
                binding.lottie.setGone()
                findNavController().navigate(R.id.action_splashScreenFragment_to_navigation_dashboard)

            }

            override fun onAnimationCancel(animation: Animator) {
            }

            override fun onAnimationRepeat(animation: Animator) {
            }

        })
    }

}