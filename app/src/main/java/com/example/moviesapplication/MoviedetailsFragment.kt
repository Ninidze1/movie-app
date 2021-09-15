package com.example.moviesapplication

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class MoviedetailsFragment : Fragment() {

    companion object {
        fun newInstance() = MoviedetailsFragment()
    }

    private lateinit var viewModel: MoviedetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.moviedetails_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MoviedetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}