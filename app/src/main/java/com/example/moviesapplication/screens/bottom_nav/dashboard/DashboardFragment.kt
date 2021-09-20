package com.example.moviesapplication.screens.bottom_nav.dashboard

import android.util.Log.d
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.paging.filter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.moviesapplication.R
import com.example.moviesapplication.adapter.dashboard.DashBoardLatestRecyclerAdapter
import com.example.moviesapplication.adapter.dashboard.DashBoardRecyclerAdapter
import com.example.moviesapplication.adapter.dashboard.GenreRecyclerAdapter
import com.example.moviesapplication.adapter.dashboard.SearchRecyclerViewAdapter
import com.example.moviesapplication.base.BaseFragment
import com.example.moviesapplication.databinding.DashboardFragmentBinding
import com.example.moviesapplication.extensions.removeDrawableEnd
import com.example.moviesapplication.extensions.setDrawableEnd
import com.example.moviesapplication.extensions.setGone
import com.example.moviesapplication.extensions.show
import com.example.moviesapplication.network.Resource
import com.example.moviesapplication.paging.loading.LoaderStateAdapter
import com.example.moviesapplication.repository.firebase.FirebaseRepository
import com.example.moviesapplication.utils.Constants.MOVIE_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
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
    private lateinit var searchAdapter: SearchRecyclerViewAdapter
    private lateinit var upComingAdapter: DashBoardLatestRecyclerAdapter

    private lateinit var snapHelper: SnapHelper

    override fun init(inflater: LayoutInflater, container: ViewGroup?) {

        binding.root.setColorSchemeResources(R.color.main_color)

        requestMovies()
        recyclerSetup()
        observers()
        listener()
    }

    private fun listener() {
        searchClick()

        binding.closeSearchBtn.setOnClickListener {
            backToNormal()
        }

        popularAdapter.onMovieClick = { movieId ->
            putInBundleAndNavigate(MOVIE_ID, movieId, R.id.action_navigation_dashboard_to_singleMovieFragment)
        }

        upComingAdapter.onPosterClick = { movieId ->
            putInBundleAndNavigate(MOVIE_ID, movieId, R.id.action_navigation_dashboard_to_singleMovieFragment)
        }

        searchAdapter.onResultClick = { movieId ->
            putInBundleAndNavigate(MOVIE_ID, movieId, R.id.action_navigation_dashboard_to_singleMovieFragment)
        }

        genresAdapter.genreClick = { genreId, genre ->
            binding.scrollview.smoothScrollBy(0, 1400)

            viewModel.popularMovies()

            binding.popularTv.text = genre
            viewModel.popularMovies().observe(viewLifecycleOwner, { data ->

                lifecycleScope.launch {
                    val filteredList = data.filter { it.genreIds?.contains(genreId) == true }
                    popularAdapter.submitData(filteredList)
                }
            })
        }

        binding.root.setOnRefreshListener {
            viewModel.upComingMovies()
            viewModel.popularMovies()
            observers()
        }
    }

    private fun searchClick() {

        binding.searchBar.doOnTextChanged { text, _, _, _ ->
            binding.searchRecycler.show()

            if (binding.searchRecycler.isVisible) {
                binding.searchBar.setBackgroundResource(R.drawable.edit_text_shape_clicked)

            }
            if (text!!.length >= 2) {
                binding.searchBar.removeDrawableEnd()
                binding.closeSearchBtn.show()

                getSearchResultWithDelay(text.toString())
            } else if (text.isEmpty()) {
                binding.searchRecycler.setGone()
                backToNormal()
            }
        }
    }

    private fun backToNormal() {
        binding.searchBar.text?.clear()
        binding.searchRecycler.setGone()
        binding.closeSearchBtn.setGone()

        binding.searchBar.setDrawableEnd(requireContext(), R.drawable.ic_search)
        binding.searchBar.setBackgroundResource(R.drawable.edit_text_shape)
    }

    private fun observers() {

        viewModel.popularMovies().observe(viewLifecycleOwner, { data ->
            lifecycleScope.launch {
                binding.root.isRefreshing = false
                d("tagta2g", "$data")
                popularAdapter.submitData(data)
            }
        })

        viewModel.upComingMovies().observe(viewLifecycleOwner, { data ->
            lifecycleScope.launch {
                d("dataCheckw", "$data")
                binding.root.isRefreshing = false
                upComingAdapter.submitData(data)
            }
        })

        viewModel.searchResult.observe(viewLifecycleOwner, { data ->
            when (data.status) {
                Resource.Status.SUCCESS -> {
                    searchAdapter.notifyItemRangeRemoved(0, searchAdapter.itemCount)
                    data.data?.results?.let { searchAdapter.addData(it.toMutableList()) }
                }
                Resource.Status.ERROR -> {
                    d("loadingErroR", "${data.message}")
                }
                Resource.Status.LOADING -> {
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

        viewModel.upComingMovies()
        viewModel.popularMovies()
        viewModel.getGenres()
    }

    private fun recyclerSetup() {
        snapHelper = LinearSnapHelper()
        popularRecycler()
        genresRecycler()
        searchRecycler()
        upComingRecycler()
    }

    private fun genresRecycler() {
        genresAdapter = GenreRecyclerAdapter()
        binding.genre.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.genre.adapter = genresAdapter
        snapHelper.attachToRecyclerView(binding.genre)
    }
    private fun upComingRecycler() {
        upComingAdapter = DashBoardLatestRecyclerAdapter()
        binding.futureRecylcer.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.futureRecylcer.adapter = upComingAdapter.withLoadStateFooter(LoaderStateAdapter())
        snapHelper.attachToRecyclerView(binding.futureRecylcer)
    }

    private fun popularRecycler() {
        popularAdapter = DashBoardRecyclerAdapter()
        binding.dashboardRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.dashboardRecycler.adapter = popularAdapter.withLoadStateFooter(LoaderStateAdapter())
    }

    private fun searchRecycler() {
        searchAdapter = SearchRecyclerViewAdapter()
        binding.searchRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.searchRecycler.adapter = searchAdapter
    }

    private fun getSearchResultWithDelay(text: String) {
        viewModel.searchMovie(text)

    }
}