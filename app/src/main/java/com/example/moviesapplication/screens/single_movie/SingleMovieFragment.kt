package com.example.moviesapplication.screens.single_movie

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.moviesapplication.R
import com.example.moviesapplication.adapter.dashboard.DashBoardLatestRecyclerAdapter
import com.example.moviesapplication.base.BaseFragment
import com.example.moviesapplication.databinding.SingleMovieFragmentBinding
import com.example.moviesapplication.entity.MovieItem
import com.example.moviesapplication.extensions.loadImg
import com.example.moviesapplication.extensions.showIf
import com.example.moviesapplication.extensions.titleAdjust
import com.example.moviesapplication.network.Resource
import com.example.moviesapplication.paging.loading.LoaderStateAdapter
import com.example.moviesapplication.utils.Constants.IMG_DOMAIN
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SingleMovieFragment : BaseFragment<SingleMovieFragmentBinding, SingleMovieViewModel>(
    SingleMovieFragmentBinding::inflate,
    SingleMovieViewModel::class.java
) {
    private lateinit var similarAdapter: DashBoardLatestRecyclerAdapter
    private var currentMovieId: Int = -1

    override fun init(inflater: LayoutInflater, container: ViewGroup?) {
        retrieveData(2)

        listeners()
        observers()
        similarMovieRecyclerSetup()
    }

    private fun listeners() {
        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_singleMovieFragment_to_navigation_dashboard)
        }
    }

    private fun observers() {
        viewModel.movieDetails.observe(viewLifecycleOwner, { data ->
            when (data.status) {
                Resource.Status.SUCCESS -> {
                    data.data?.let { bindData(it) }
                }
                Resource.Status.ERROR -> {
                    Log.d("loadingErroR", "${data.message}")

                }
                Resource.Status.LOADING -> {
                }
            }
        })

        viewModel.similarMovies(currentMovieId).observe(viewLifecycleOwner, { data ->
            lifecycleScope.launch {
                similarAdapter.submitData(data)
            }
        })
    }

    private fun bindData(data: MovieItem) {
        binding.titleTv.text = data.title.toString()
        binding.languageTv2.text = data.originalLanguage.toString()
        binding.yearTv2.text = data.releaseDate?.substring(0, 4) ?: "Error"
        binding.descriptionTv.text = data.overview.toString()

        binding.posterImg.loadImg(IMG_DOMAIN + data.posterPath.toString())
        binding.backgroundPoster.loadImg(IMG_DOMAIN + data.backdropPath.toString())

        data.adult?.let { binding.ageLimit.showIf(it) }
        binding.titleTv.titleAdjust()

    }

    private fun retrieveData(movieId: Int) {
        currentMovieId = movieId

        viewModel.getMovieDetails(movieId)
        viewModel.similarMovies(movieId)

    }

    private fun similarMovieRecyclerSetup() {
        similarAdapter = DashBoardLatestRecyclerAdapter()
        binding.similarRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.similarRecycler.adapter = similarAdapter.withLoadStateFooter(LoaderStateAdapter())
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.similarRecycler)
    }



}