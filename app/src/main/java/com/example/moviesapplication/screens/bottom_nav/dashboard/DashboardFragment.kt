package com.example.moviesapplication.screens.bottom_nav.dashboard

import android.util.Log.d
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.moviesapplication.adapter.DashBoardRecyclerAdapter
import com.example.moviesapplication.adapter.GenreRecyclerAdapter
import com.example.moviesapplication.base.BaseFragment
import com.example.moviesapplication.databinding.DashboardFragmentBinding
import com.example.moviesapplication.extensions.setGone
import com.example.moviesapplication.extensions.show
import com.example.moviesapplication.network.Resource
import com.example.moviesapplication.repository.firebase.FirebaseRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : BaseFragment<DashboardFragmentBinding, DashboardViewModel>(
    DashboardFragmentBinding::inflate,
    DashboardViewModel::class.java
) {

    @Inject
    lateinit var auth: FirebaseRepository

    private lateinit var popularAdapter: DashBoardRecyclerAdapter
    private lateinit var genresAdapter: GenreRecyclerAdapter

    private lateinit var snapHelper: SnapHelper

    override fun init(inflater: LayoutInflater, container: ViewGroup?) {
        requestMovies()
        recyclerSetup()
        observers()
        listener()
    }

    private fun listener() {
        genresAdapter.genreClick = { genre ->

            viewModel.searchMovie(genre)
        }
    }

    private fun observers() {

        viewModel.popularMovies.observe(viewLifecycleOwner, { data ->
            when (data.status) {
                Resource.Status.SUCCESS -> {
                    binding.loadingAnim.setGone()
                    data.data?.results?.let { popularAdapter.addItems(it.toMutableList()) }
                }
                Resource.Status.ERROR -> {
                    d("loadingErroR", "${data.message}")
                }
                Resource.Status.LOADING -> {
                    binding.loadingAnim.show()
                }
            }
        })

        viewModel.genres.observe(viewLifecycleOwner, { data ->

            when (data.status) {
                Resource.Status.SUCCESS -> {
                    d("loadingErroR", "${data.data}2")

                    data.data?.result?.let { genresAdapter.addItems(it.toMutableList()) }
                }
                Resource.Status.ERROR -> {
                    d("loadingErroR", "${data.message}")
                }
                Resource.Status.LOADING -> { }
            }
        })
    }

    private fun requestMovies() {
        binding.loadingAnim.show()
        viewModel.getPopularMovies()
        viewModel.getGenres()
    }

    private fun recyclerSetup() {
        snapHelper = LinearSnapHelper()
        popularRecycler()
        genresRecycler()
    }

    private fun genresRecycler() {

        genresAdapter = GenreRecyclerAdapter()
        binding.genre.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.genre.adapter = genresAdapter
        snapHelper.attachToRecyclerView(binding.genre)
    }

    private fun popularRecycler() {
        popularAdapter = DashBoardRecyclerAdapter()
        binding.dashboardRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.dashboardRecycler.adapter = popularAdapter
        snapHelper.attachToRecyclerView(binding.dashboardRecycler)
    }
}