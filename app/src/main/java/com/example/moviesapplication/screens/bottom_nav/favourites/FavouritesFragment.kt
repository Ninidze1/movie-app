package com.example.moviesapplication.screens.bottom_nav.favourites

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.moviesapplication.base.BaseFragment
import com.example.moviesapplication.databinding.FavouritesFragmentBinding

class FavouritesFragment : BaseFragment<FavouritesFragmentBinding, FavouritesViewModel>(
    FavouritesFragmentBinding::inflate,
    FavouritesViewModel::class.java
) {
    override fun init(inflater: LayoutInflater, container: ViewGroup?) {

    }


}