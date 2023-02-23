package com.example.moviesapplication.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.common.extensions.loadImg
import com.example.common.extensions.titleAdjustForDash
import com.example.common.utils.Constants.IMG_DOMAIN
import com.example.data.dto.dashboard.MovieItem
import com.example.moviesapplication.databinding.PosterItemBinding

typealias movieClick = (movieId: Int) -> Unit
class DashBoardRecyclerAdapter: PagingDataAdapter<MovieItem, DashBoardRecyclerAdapter.ItemHolder>(
    REPO_COMPARATOR
) {
    private val items: MutableList<MovieItem> = mutableListOf()
    lateinit var onMovieClick: movieClick

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

    inner class ItemHolder(private val binding: PosterItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var model: MovieItem
        fun bind() {
            model = getItem(absoluteAdapterPosition)!!

            binding.title.titleAdjustForDash()
            if (model.releaseDate!!.isNotEmpty()) {
                val year = model.releaseDate?.substring(0, 4)
                binding.yearTv.text = year
            }
            binding.title.text = model.title
            binding.rating.text = model.voteAverage.toString()

            if (model.originalLanguage?.isNotEmpty() == true)
                binding.languageTv.text = model.originalLanguage.toString()
            model.posterPath?.let { binding.imageView.loadImg(IMG_DOMAIN + model.posterPath) }

            binding.root.setOnClickListener {
                model.id?.let { it1 -> onMovieClick.invoke(it1) }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(PosterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = items.size

}