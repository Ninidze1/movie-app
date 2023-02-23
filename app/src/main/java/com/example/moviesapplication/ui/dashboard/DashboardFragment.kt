package com.example.moviesapplication.ui.dashboard

import android.util.Log.d
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.filter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.common.base.BaseFragment
import com.example.common.extensions.removeDrawableEnd
import com.example.common.extensions.setDrawableEnd
import com.example.common.extensions.setGone
import com.example.common.extensions.show
import com.example.common.utils.Constants.MOVIE_ID
import com.example.data.network.Resource
import com.example.data.paging.loading.LoaderStateAdapter
import com.example.moviesapplication.R
import com.example.moviesapplication.databinding.DashboardFragmentBinding
import com.example.moviesapplication.ui.dashboard.adapter.DashBoardLatestRecyclerAdapter
import com.example.moviesapplication.ui.dashboard.adapter.DashBoardRecyclerAdapter
import com.example.moviesapplication.ui.dashboard.adapter.GenreRecyclerAdapter
import com.example.moviesapplication.ui.dashboard.adapter.SearchRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardFragment : BaseFragment<DashboardFragmentBinding>(
    DashboardFragmentBinding::inflate
) {

    private lateinit var popularAdapter: DashBoardRecyclerAdapter
    private lateinit var genresAdapter: GenreRecyclerAdapter
    private lateinit var searchAdapter: SearchRecyclerViewAdapter
    private lateinit var upComingAdapter: DashBoardLatestRecyclerAdapter

    private lateinit var snapHelper: SnapHelper

    private val viewModel by viewModels<DashboardViewModel>()

    override fun init() {
        requestMovies()
        recyclerSetup()
        observers()
        listener()
    }

    private fun listener() {

        binding.scrollView.setOnScrollChangeListener { _: View, _: Int, scrollY: Int, _: Int, _: Int ->
            binding.scrollView.isEnabled = scrollY < 25
        }

        searchClick()

        binding.included.closeSearchBtn.setOnClickListener {
            backToNormal()
        }

        popularAdapter.onMovieClick = { movieId ->
            findNavController().navigate(
                R.id.action_navigation_dashboard_to_singleMovieFragment,
                bundleOf(MOVIE_ID to movieId)
            )
        }

        upComingAdapter.onPosterClick = { movieId ->
            findNavController().navigate(
                R.id.action_navigation_dashboard_to_singleMovieFragment,
                bundleOf(MOVIE_ID to movieId)
            )
        }

        searchAdapter.onResultClick = { movieId ->
            findNavController().navigate(
                R.id.action_navigation_dashboard_to_singleMovieFragment,
                bundleOf(MOVIE_ID to movieId)
            )
        }

        genresAdapter.genreClick = { genreId, genre ->
            binding.scrollView.smoothScrollBy(0, 1500)

            viewModel.popularMovies()

            binding.popularTv.text = genre
            viewModel.popularMovies().observe(viewLifecycleOwner) { data ->

                lifecycleScope.launch {
                    val filteredList = data.filter { it.genreIds?.contains(genreId) == true }
                    popularAdapter.submitData(filteredList)
                }
            }
        }

    }

    private fun searchClick() {

        binding.included.searchBar.doOnTextChanged { text, _, _, _ ->
            binding.included.searchRecycler.show()

            if (binding.included.searchRecycler.isVisible) {
                binding.included.searchBar.setBackgroundResource(R.drawable.edit_text_shape_clicked)

            }
            if (text!!.length >= 2) {
                binding.included.searchBar.removeDrawableEnd()
                binding.included.closeSearchBtn.show()

                getSearchResultWithDelay(text.toString())
            } else if (text.isEmpty()) {
                binding.included.searchRecycler.setGone()
                backToNormal()
            }
        }
    }

    private fun backToNormal() {
        binding.included.searchBar.text?.clear()
        binding.included.searchRecycler.setGone()
        binding.included.closeSearchBtn.setGone()

        binding.included.searchBar.setDrawableEnd(requireContext(), R.drawable.ic_search)
        binding.included.searchBar.setBackgroundResource(R.drawable.edit_text_shape)
    }

    private fun observers() {

        viewModel.popularMovies().observe(viewLifecycleOwner) { data ->
            lifecycleScope.launch {
                popularAdapter.submitData(data)
            }
        }

        viewModel.upComingMovies().observe(viewLifecycleOwner) { data ->
            lifecycleScope.launch {
                upComingAdapter.submitData(data)
            }
        }

        viewModel.searchResult.observe(viewLifecycleOwner) { data ->
            when (data.status) {
                Resource.Status.SUCCESS -> {
                    searchAdapter.notifyItemRangeRemoved(0, searchAdapter.itemCount)
                    data.data?.results?.let { searchAdapter.addData(it.toMutableList()) }
                }
                Resource.Status.ERROR -> {
                    d("loadingError", "${data.message}")
                }
                Resource.Status.LOADING -> {
                }
            }
        }

        viewModel.genres.observe(viewLifecycleOwner) { data ->

            when (data.status) {
                Resource.Status.SUCCESS -> {
                    d("loadingError", "${data.data}2")

                    data.data?.result?.let { genresAdapter.addItems(it.toMutableList()) }
                }
                Resource.Status.ERROR -> {
                    d("loadingError", "${data.message}")
                }
                Resource.Status.LOADING -> {}
            }
        }
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
        binding.genre.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.genre.adapter = genresAdapter
        snapHelper.attachToRecyclerView(binding.genre)
    }

    private fun upComingRecycler() {
        upComingAdapter = DashBoardLatestRecyclerAdapter()
        binding.futureRecylcer.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
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
        binding.included.searchRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.included.searchRecycler.adapter = searchAdapter
    }

    private fun getSearchResultWithDelay(text: String) {
        viewModel.searchMovie(text)

    }
}