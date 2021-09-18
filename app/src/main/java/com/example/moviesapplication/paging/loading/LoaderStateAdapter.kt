package com.example.moviesapplication.paging.loading

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapplication.R

class LoaderStateAdapter :
    LoadStateAdapter<LoaderStateAdapter.LoaderViewHolder>() {

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        return LoaderViewHolder.getInstance(parent)
    }

    class LoaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        companion object {
            fun getInstance(parent: ViewGroup): LoaderViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view =
                    inflater.inflate(R.layout.item_recyclerview_load_more_layout, parent, false)
                return LoaderViewHolder(view)
            }
        }

        private val motionLayout: ProgressBar = view.findViewById(R.id.progressBar)

        fun bind(loadState: LoadState) {
            motionLayout.isVisible = loadState is LoadState.Loading
        }
    }
}