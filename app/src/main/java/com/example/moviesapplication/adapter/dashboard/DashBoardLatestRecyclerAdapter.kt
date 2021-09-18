package com.example.moviesapplication.adapter.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapplication.databinding.LatestPosterItemBinding
import com.example.moviesapplication.entity.MovieItem
import com.example.moviesapplication.extensions.loadImg
import com.example.moviesapplication.utils.Constants.IMG_DOMAIN

class DashBoardLatestRecyclerAdapter: PagingDataAdapter<MovieItem, DashBoardLatestRecyclerAdapter.ItemHolder>(
    REPO_COMPARATOR
) {
    private val items: MutableList<MovieItem> = mutableListOf()

    companion object {

        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<MovieItem>() {
            override fun areItemsTheSame(
                oldItem: MovieItem,
                newItem: MovieItem
            ): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(
                oldItem: MovieItem,
                newItem: MovieItem
            ): Boolean =
                oldItem.id == newItem.id
        }
    }

    inner class ItemHolder(private val binding: LatestPosterItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var model: MovieItem
        fun bind() {
            model = getItem(absoluteAdapterPosition)!!

            model.posterPath?.let { binding.imageView.loadImg(IMG_DOMAIN + model.posterPath) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(LatestPosterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = items.size

}