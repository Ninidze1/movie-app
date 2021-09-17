package com.example.moviesapplication.screens.bottom_nav.dashboard

import android.util.Log.d
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.moviesapplication.adapter.DashBoardRecyclerAdapter
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
    private lateinit var adapter: DashBoardRecyclerAdapter

    override fun init(inflater: LayoutInflater, container: ViewGroup?) {
        requestMovies()
        recyclerSetup()
        observers()
        listener()
    }

    private fun listener() {

    }

    private fun observers() {

        viewModel.popularMovies.observe(viewLifecycleOwner, { data ->
            when (data.status) {
                Resource.Status.SUCCESS -> {
                    binding.loadingAnim.setGone()
                    data.data?.results?.let { adapter.addItems(it.toMutableList()) }
                }
                Resource.Status.ERROR -> {
                    d("loadingErroR", "${data.message}")
                }
                Resource.Status.LOADING -> {
                    binding.loadingAnim.show()
                }
            }
        })
    }

    private fun requestMovies() {
        binding.loadingAnim.show()
        viewModel.getPopularMovies()
    }

    private fun recyclerSetup() {
        adapter = DashBoardRecyclerAdapter()
        binding.dashboardRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.dashboardRecycler.adapter = adapter

        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.dashboardRecycler)
    }
}