package com.example.moviesapplication.ui.single_movie

import android.util.Log.d
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.common.base.BaseFragment
import com.example.common.extensions.*
import com.example.common.utils.Constants.IMG_DOMAIN
import com.example.common.utils.Constants.MOVIE_ID
import com.example.data.dto.dashboard.MovieItem
import com.example.data.dto.person.FavMovie
import com.example.data.network.Resource
import com.example.data.paging.loading.LoaderStateAdapter
import com.example.moviesapplication.R
import com.example.moviesapplication.databinding.SingleMovieFragmentBinding
import com.example.moviesapplication.ui.dashboard.adapter.DashBoardLatestRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt


@AndroidEntryPoint
class SingleMovieFragment : BaseFragment<SingleMovieFragmentBinding>(
    SingleMovieFragmentBinding::inflate
) {

    private val viewModel by viewModels<SingleMovieViewModel>()

    private lateinit var similarAdapter: DashBoardLatestRecyclerAdapter
    private lateinit var castAdapter: CastRecyclerAdapter

    private var isFav = false

    private var currentMovieId: Int = -1

    override fun init() {
        retrieveData()
        setUpRecyclerViews()

        listeners()
        observers()

    }

    private fun setUpRecyclerViews() {
        val snapHelper = LinearSnapHelper()
        similarMovieRecyclerSetup(snapHelper)
        castRecyclerSetup(snapHelper)
    }

    private fun listeners() {
        similarAdapter.onPosterClick = { movieId ->
            findNavController().navigate(
                R.id.singleMovieFragment,
                bundleOf(MOVIE_ID to movieId)
            )
        }

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_singleMovieFragment_to_navigation_dashboard)
        }

    }

    private fun observers() {
        viewModel.movieDetails.observe(viewLifecycleOwner) { data ->
            when (data.status) {
                Resource.Status.SUCCESS -> {
                    data.data?.let { bindData(it) }
                    viewModel.similarMovies(currentMovieId)
                    viewModel.getMovieActors(currentMovieId)

                    loadSimilarMovies()
                }
                Resource.Status.ERROR -> {
                    d("loadingError", "${data.message}")

                }
                Resource.Status.LOADING -> {
                }
            }
        }

        viewModel.movieCast.observe(viewLifecycleOwner) { data ->
            when (data.status) {
                Resource.Status.SUCCESS -> {
                    data.data?.cast?.let {
                        d("tagtag", "${data.data}")
                        castAdapter.addItems(it.toMutableList())
                    }
                }
                Resource.Status.ERROR -> {
                    d("loadingError", "${data.message}")
                }
                Resource.Status.LOADING -> {
                }
            }
        }
    }

    private fun loadSimilarMovies() {
        viewModel.similarMovies(currentMovieId).observe(viewLifecycleOwner) { data ->
            lifecycleScope.launch {
                similarAdapter.submitData(data)
            }
        }
    }

    private fun bindData(data: MovieItem) {
        val year = data.releaseDate?.substring(0, 4) ?: "Error"

        CoroutineScope(Dispatchers.Main).launch {
            val ids = withContext(Dispatchers.Default) {
                viewModel.getAllIds()
            }
            isFav = if (data.id in ids) {
                binding.toFavButton.setDrawable(R.drawable.ic_fav)
                true
            } else {
                binding.toFavButton.setDrawable((R.drawable.ic_fav_passive))
                false
            }
        }

        binding.toFavButton.setOnClickListener {
            if (!isFav) {
                viewModel.addToFav(
                    FavMovie(
                        movieId = data.id,
                        title = data.title,
                        vote = data.voteAverage,
                        lang = data.originalLanguage,
                        year = year,
                        poster = data.posterPath
                    )
                )
                isFav = true
                binding.toFavButton.setDrawable(R.drawable.ic_fav)
            } else {
                isFav = false
                data.id?.let { it1 -> viewModel.removeFromFav(it1) }
                binding.toFavButton.setDrawable(R.drawable.ic_fav_passive)
            }
        }

        binding.titleTv.text = data.title.toString()
        binding.languageTv2.text = data.originalLanguage.toString()
        binding.yearTv2.text = year
        binding.descriptionTv.text = data.overview.toString()

        binding.posterImg.loadImg(IMG_DOMAIN + data.posterPath.toString())

        binding.backgroundPoster.loadImg(IMG_DOMAIN + data.backdropPath.toString())
        binding.shimmerLayout.blurImg()
        binding.shimmerPosterLayout.blurImg()

        data.adult?.let { binding.ageLimit.showIf(it) }
        binding.titleTv.titleAdjust()

        binding.imdbTv.text = data.voteAverage.toString()
        data.voteAverage?.let { rating ->
            displayStarts(rating)
        }

    }

    private fun displayStarts(votes: Double) {
        val stars = (votes.roundToInt() / 2)
        val starList = mutableListOf<AppCompatImageView>()

        createStarView(stars, starList)
        starList.createConstraint(
            binding.imdbTv,
            binding.mainRoot
        )
    }

    private fun createStarView(
        stars: Int,
        starList: MutableList<AppCompatImageView>
    ) {
        for (each in 1..stars) {
            val newStar = AppCompatImageView(requireContext())
            newStar.id = View.generateViewId()
            binding.mainRoot.addView(newStar)
            starList.add(newStar)

            newStar.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            newStar.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
            newStar.setImageResource(R.drawable.ic_fav)
        }
    }

    private fun retrieveData() {
        val movie = arguments?.getInt(MOVIE_ID)
        if (movie != null) {
            currentMovieId = movie

            viewModel.getMovieDetails(currentMovieId)
        }
    }

    private fun similarMovieRecyclerSetup(snapHelper: LinearSnapHelper) {
        similarAdapter = DashBoardLatestRecyclerAdapter()
        binding.similarRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.similarRecycler.adapter = similarAdapter.withLoadStateFooter(LoaderStateAdapter())
        snapHelper.attachToRecyclerView(binding.similarRecycler)
    }

    private fun castRecyclerSetup(snapHelper: LinearSnapHelper) {
        castAdapter = CastRecyclerAdapter()
        binding.actorsRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.actorsRecycler.adapter = castAdapter
        snapHelper.attachToRecyclerView(binding.actorsRecycler)
    }

}