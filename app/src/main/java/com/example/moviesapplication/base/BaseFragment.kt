package com.example.moviesapplication.base

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.biometric.BiometricPrompt
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import java.util.concurrent.Executor


typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel>(
    private val inflate: Inflate<VB>,
    private val baseViewModel: Class<VM>
) : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    protected val viewModel: VM by lazy {
        ViewModelProvider(this).get(baseViewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate(inflater, container, false)
        init(inflater, container)

        return binding.root
    }

    abstract fun init(inflater: LayoutInflater, container: ViewGroup?)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected fun putInBundleAndNavigate(key: String ,movieId: Any, destination: Int) {
        val bundle = bundleOf(key to movieId)
        findNavController().navigate(
            destination,
            bundle
        )
    }

}