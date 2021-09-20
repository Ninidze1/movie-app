package com.example.moviesapplication.screens.bottom_nav.profile.bottom_sheet

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.moviesapplication.R
import com.example.moviesapplication.databinding.BottomSheetFragmentBinding
import com.example.moviesapplication.extensions.show
import com.example.moviesapplication.extensions.showToast
import com.example.moviesapplication.repository.firebase.FirebaseRepository
import com.example.moviesapplication.utils.Constants
import com.example.moviesapplication.utils.Constants.RESET_PIN
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.Executor
import javax.inject.Inject

@AndroidEntryPoint
class BottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetFragmentBinding
    private val viewModel: BottomSheetViewModel by viewModels()

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    @Inject
    lateinit var auth: FirebaseRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetFragmentBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return super.onCreateDialog(savedInstanceState).apply {
            window?.setDimAmount(0.5f)
            setOnShowListener {
                val bottomSheet = findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
                bottomSheet.setBackgroundResource(android.R.color.transparent)
            }
        }
    }

    private fun init() {
        biometricAuth()
        checkIfDev()
        listeners()
    }

    private fun checkIfDev() {
        val isDev = arguments?.get(Constants.DEV_PERM)
        if (isDev != null && isDev == true) {
            binding.goOnIdButton.show()
        }
    }

    private fun listeners() {
        binding.signOut.setOnClickListener {
            auth.signOut()
            viewModel.deleteSession()
            findNavController().navigate(R.id.main_auth)
        }
        binding.resetBtn.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }

        binding.clearFav.setOnClickListener {
            viewModel.clearFavourites()
            findNavController().navigate(R.id.action_bottomSheetFragment_to_navigation_profile)
        }
    }

    private fun biometricAuth() {

        executor = ContextCompat.getMainExecutor(requireContext())
        biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {

            @SuppressLint("SwitchIntDef")
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Log.d("loglog", "$errorCode $errString")

                when (errorCode) {
                    BiometricPrompt.ERROR_HW_UNAVAILABLE -> requireActivity().showToast(errString as String)
                    BiometricPrompt.ERROR_LOCKOUT -> requireActivity().showToast(errString as String)
                    BiometricPrompt.ERROR_NO_BIOMETRICS -> {
                        startActivity(Intent(Settings.ACTION_SECURITY_SETTINGS))
                        requireActivity().showToast("Enable biometric authentication from Settings", Toast.LENGTH_LONG)
                    }
                }
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                val bundle = bundleOf(RESET_PIN to true)
                findNavController().navigate(
                    R.id.action_bottomSheetFragment_to_securityScreenFragment,
                    bundle
                )
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                requireContext().showToast("You are not my master")
            }
        })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Simple authentication")
            .setSubtitle("Log in using your fingerprint")
            .setNegativeButtonText("Use passcode")
            .build()
    }


}