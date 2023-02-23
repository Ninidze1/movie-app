package com.example.moviesapplication.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.base.BaseFragment
import com.example.common.utils.Constants.MOVIE_ID
import com.example.moviesapplication.R
import com.example.moviesapplication.databinding.ProfileFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class ProfileFragment : BaseFragment<ProfileFragmentBinding>(
    ProfileFragmentBinding::inflate
) {
    private lateinit var adapter: FavouritesRecyclerAdapter

    private val viewModel by viewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.sessionStatus.observe(viewLifecycleOwner) { session ->
            when {
                (viewModel.isSessionActive() && session == false) -> findNavController().navigate(R.id.securityScreenFragment)
                !viewModel.isSessionActive() -> findNavController().navigate(R.id.main_auth)
            }
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun init() {
        initRecycler()
        deliverData()
        listeners()
    }

    private fun listeners() {
        binding.hamburgerMenu.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_profile_to_bottomSheetFragment)
        }
        adapter.onFavouriteClick = { movieId ->
            val bundle = bundleOf(MOVIE_ID to movieId)
            findNavController().navigate(
                R.id.action_navigation_profile_to_singleMovieFragment,
                bundle
            )
        }
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initRecycler() {
        adapter = FavouritesRecyclerAdapter()
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter
    }

    private fun deliverData() {
        CoroutineScope(Dispatchers.Main).launch {
            val favMovies = withContext(Dispatchers.Default) {
                viewModel.getAllFavourites()
            }
            adapter.addData(favMovies.toMutableList())
        }
    }


}