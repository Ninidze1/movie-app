package com.example.moviesapplication.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.common.extensions.loadImg
import com.example.common.utils.Constants.IMG_DOMAIN
import com.example.data.dto.dashboard.MoviePoster
import com.example.moviesapplication.databinding.LatestPosterItemBinding

typealias posterClick = (movieId: Int) -> Unit
class DashBoardLatestRecyclerAdapter: PagingDataAdapter<MoviePoster, DashBoardLatestRecyclerAdapter.ItemHolder>(
    REPO_COMPARATOR
) {
    private val items: MutableList<MoviePoster> = mutableListOf()
    lateinit var onPosterClick: posterClick

    companion object {

        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<MoviePoster>() {
            override fun areItemsTheSame(
                oldItem: MoviePoster,
                newItem: MoviePoster
            ): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(
                oldItem: MoviePoster,
                newItem: MoviePoster
            ): Boolean =
                oldItem.id == newItem.id
        }
    }

    inner class ItemHolder(private val binding: LatestPosterItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var model: MoviePoster
        fun bind() {
            model = getItem(absoluteAdapterPosition)!!

            model.posterPath?.let { binding.imageView.loadImg(IMG_DOMAIN + model.posterPath) }

            binding.root.setOnClickListener {
                model.id?.let { it1 -> onPosterClick.invoke(it1) }
            }
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