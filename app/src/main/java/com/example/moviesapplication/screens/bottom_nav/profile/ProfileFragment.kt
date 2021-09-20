package com.example.moviesapplication.screens.bottom_nav.profile

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesapplication.R
import com.example.moviesapplication.adapter.profile.FavouritesRecyclerAdapter
import com.example.moviesapplication.base.BaseFragment
import com.example.moviesapplication.databinding.ProfileFragmentBinding
import com.example.moviesapplication.extensions.showToast
import com.example.moviesapplication.repository.firebase.FirebaseRepository
import com.example.moviesapplication.utils.Constants
import com.example.moviesapplication.utils.Constants.DEV_PERM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment<ProfileFragmentBinding, ProfileViewModel>(
    ProfileFragmentBinding::inflate,
    ProfileViewModel::class.java
) {
    @Inject
    lateinit var auth: FirebaseRepository
    private lateinit var adapter: FavouritesRecyclerAdapter
    private var isDeveloper = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.sessionStatus.observe(viewLifecycleOwner, { session ->
            d("tagtag", "$session")
            when {
                (auth.getUser()?.uid != null && session == false) -> findNavController().navigate(R.id.securityScreenFragment)
                auth.getUser()?.uid == null -> findNavController().navigate(R.id.main_auth)
            }
        })
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun init(inflater: LayoutInflater, container: ViewGroup?) {
        initRecycler()
        deliverData()
        listeners()
        observers()
    }

    private fun listeners() {
        binding.hamburgerMenu.setOnClickListener {
            putInBundleAndNavigate(DEV_PERM, true, R.id.action_navigation_profile_to_bottomSheetFragment)
        }
        adapter.onFavouriteClick = { movieId ->
            putInBundleAndNavigate(Constants.MOVIE_ID, movieId, R.id.action_navigation_profile_to_singleMovieFragment)
        }
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.Favourites.setOnClickListener {
            viewModel.developerFeature()
        }
    }

    private fun observers() {
        viewModel.clickedAmount.observe(viewLifecycleOwner, { clicked ->

            if (clicked >= 3) {
                requireContext().showToast("${9-clicked} steps to developer")
                if (clicked == 9) {
                    isDeveloper = true
                }
            }
        })
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